package com.laodyu.web.activiti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.laodyu.commons.exception.BizException;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.web.activiti.entity.NewModel;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/7
 * @Version 1.0
 **/
@RestController
@RequestMapping("/activiti")
public class ActivitiController {
    private final String MODEL_NAME = "name";
    private final String MODEL_DESCRIPTION = "description";
    private final String MODEL_REVISION = "revision";
    private final String MODEL_TENANTID = "88888888";
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 获取所有模型列表
     *
     * @return
     */
    @PostMapping("/getAllModel")
    public RespPage search(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.orderByLastUpdateTime().desc();
        modelQuery.modelTenantIdLike(MODEL_TENANTID);
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        long total = modelQuery.count();
        List<Model> models = modelQuery.listPage(page, size);
        RespPage respPage = new RespPage(total,models);
        return respPage;
    }

    /***
     * 创建一个新模型
     * @param newModel
     * @return
     */
    @PostMapping("/create")
    public RespBean create(@RequestBody NewModel newModel) {

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(MODEL_NAME, newModel.getName());
        modelNode.put(MODEL_DESCRIPTION, newModel.getDesc());
        modelNode.put(MODEL_REVISION, "1");
        modelNode.put(MODEL_TENANTID, MODEL_TENANTID);
        Model model = repositoryService.newModel();
        model.setName(newModel.getName());
        model.setKey(newModel.getKey());
        model.setTenantId(MODEL_TENANTID);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.putPOJO("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes(StandardCharsets.UTF_8));
        return RespBean.ok("创建成功！");
    }

    @GetMapping("/drowModel/{modelid}")
    public void drow(HttpServletRequest request, HttpServletResponse response, @PathVariable String modelid) throws IOException {
        response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + Integer.valueOf(modelid));
    }

    /***
     * 流程部署
     * @param modelId
     * @return
     */
    @GetMapping("/deployment/{modelId}")
    public RespBean deployment(@PathVariable String modelId) {
        // 获取模型
        Model modelData = repositoryService.getModel(modelId);

        if (modelData == null) {
            return RespBean.error("模型不存在");
        }

        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return RespBean.error("请先设计流程定义并成功保存，再进行部署");
        }

        JsonNode modelNode = null;
        try {
            modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size() == 0) {
                return RespBean.error("流程定义不符要求，请至少设计一条主线流程");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            //发布流程
            String processName = modelData.getKey() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .key(modelData.getKey())
                    .category(modelData.getCategory())
                    .addString(processName, new String(bpmnBytes, StandardCharsets.UTF_8))
                    .enableDuplicateFiltering() // 在部署时会检测已部署的相同文件的最后一条记录，如果内容相同，则不会部署
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespBean.ok("部署成功！");
    }

    /***
     * 删除模型
     */
    @DeleteMapping("/delete/{id}")
    public RespBean deleteModel(@PathVariable String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        if (model == null) {
            return RespBean.error("模型不存在");
        }
        repositoryService.deleteModel(id);
        return RespBean.ok("删除模型成功！");
    }

    /***
     * 查找模型
     * @param name
     * @return
     */
    @GetMapping("/query/{name}")
    public RespPage query(@PathVariable String name, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(name)) {
            throw new BizException("搜索时模型名称不能为空！");
        }
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.modelNameLike("%" + name + "%");
        modelQuery.orderByLastUpdateTime().desc();
        if (page != null && size != null) {
            page = (page-1) * size;
        }
        long count = modelQuery.count();
        List<Model> models = modelQuery.listPage(page, size);
        RespPage respPage = new RespPage(count,models);
        return respPage;
    }

}
