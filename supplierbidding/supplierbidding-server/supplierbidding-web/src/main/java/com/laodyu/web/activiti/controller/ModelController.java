package com.laodyu.web.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@RestController
@RequestMapping("/service")
public class ModelController {
    private final String MODEL_ID = "modelId";
    private final String MODEL_NAME = "name";
    private final String MODEL_DESCRIPTION = "description";
    private final String MODEL_REVISION = "revision";
    private final String MODEL_TENANTID = "88888888";
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;
    @GetMapping(value = "/editor/stencilset")
    public String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(Objects.requireNonNull(stencilsetStream), "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }
    /**
     * 获取流程定义json数据
     *
     * @param modelId
     * @return
     */
    @GetMapping(value = "model/{modelId}/json")
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        ObjectNode modelNode = null;

        Model model = repositoryService.getModel(modelId);

        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                byte[] modelEditorSource = repositoryService.getModelEditorSource(model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(new String(modelEditorSource, StandardCharsets.UTF_8));
                modelNode.putPOJO("model", editorJsonNode);

            } catch (Exception e) {
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return modelNode;
    }

    /**
     * 保存流程定义数据
     */
    @PutMapping(value = "model/{modelId}/save")
    public void saveModel(@PathVariable String modelId, @RequestParam("name") String name,
                          @RequestParam("json_xml") String json_xml,
                          @RequestParam("svg_xml") String svg_xml,
                          @RequestParam("description") String description) {
        try {
            Model model = repositoryService.getModel(modelId);
            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
            modelJson.put(MODEL_NAME, name);
            modelJson.put(MODEL_DESCRIPTION, description);
            model.setMetaInfo(modelJson.toString());
            model.setName(name);
            model.setVersion(model.getVersion() + 1);
            model.setTenantId(MODEL_TENANTID);
            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), Objects.requireNonNull(json_xml.getBytes(StandardCharsets.UTF_8)));
            InputStream svgStream = new ByteArrayInputStream(Objects.requireNonNull(svg_xml.getBytes(StandardCharsets.UTF_8)));
            TranscoderInput input = new TranscoderInput(svgStream);
            PNGTranscoder transcoder = new PNGTranscoder();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
        } catch (Exception e) {
            throw new ActivitiException("Error saving model", e);
        }
    }

}
