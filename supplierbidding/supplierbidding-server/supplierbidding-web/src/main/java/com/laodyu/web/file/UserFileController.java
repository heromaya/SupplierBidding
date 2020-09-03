package com.laodyu.web.file;

import com.laodyu.commons.exception.BizException;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.Supplier;
import com.laodyu.entity.SysProject;
import com.laodyu.entity.User;
import com.laodyu.entity.Userfile;
import com.laodyu.service.ProjectService;
import com.laodyu.service.SupplierService;
import com.laodyu.service.UserFileService;
import com.laodyu.web.util.SessionUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/30
 * @Version 1.0
 **/
@RestController
@RequestMapping("/file")
public class UserFileController {
    @Autowired
    ProjectService projectService;
    @Autowired
    UserFileService userFileService;

    /***
     * 保存文件
     * @return
     */
    @PostMapping("/saveFile")
    public RespBean saveFile(@RequestParam Map<String, String> params) {
        //检查该项目是否存在招标文件
        int projectId = Integer.valueOf(params.get("projectId"));
        String type = params.get("type");
        Userfile userfile = userFileService.findByProjectIdAndType(projectId, type);
        if (userfile != null) {
            if (type.equals("1")) {
                //招标文件
                throw new BizException("该项目下已经存在招标文件，如需再次上传，须先删除！");
            } else if (type.equals("2")) {
                //获取供应商的urid
                User user = SessionUtil.getSession();
                String code = user.getCode();
                //检查是否存在投标文件
                Userfile userfile1 = userFileService.checkBidBookIsExist(projectId, code, type);
                if (userfile1 != null) {
                    //投标文件
                    throw new BizException("你在该项目下已有投标文件，请先删除！");
                }
            }

        }
        userFileService.saveFile(params, SessionUtil.getSession());
        return RespBean.ok("保存成功！");
    }

    /***
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public void importData(MultipartFile file, String projectId, String type) throws IOException {
        User user = SessionUtil.getSession();
        String code = user.getCode();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        String str1 = sdf1.format(d1);
        File target = new File("E:\\SupplierBidding\\uploadFile\\" + code + "\\" + str1);
        if (!target.exists()) {
            target.mkdirs();
        }
        String filename = file.getOriginalFilename();
        String path = target + "\\" + filename;
        file.transferTo(new File(path));
        //更新数据库中文件附件地址
        userFileService.updateUrl(path, projectId, type, filename);
    }


    @GetMapping("/getAllFile")
    public RespPage getAllFile(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        page = page - 1;
        User currentUser = SessionUtil.getSession();
        RespPage allByUserid = userFileService.findAllByUserid(page, size, currentUser.getUrid());
        RespPage respPage = allByUserid;
        return respPage;
    }

    @GetMapping("/download")
    public void download(int urid, HttpServletResponse response, HttpServletRequest request) {
        if (urid == 0) {
            throw new BizException("缺少参数！");
        }
        Userfile userfile = userFileService.findByUrid(urid);
        if (userfile == null) {
            throw new BizException("未找到文件！");
        }
        String path = userfile.getUrl();
        String fileName = userfile.getUploadname();
        OutputStream os = null;
        InputStream is = null;
        try {
            //获取输入流
            os = response.getOutputStream();
            //清空输入流
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
            File file = new File(path);
            is = new FileInputStream(file);
            if (is == null) {
                throw new BizException("下载文件失败！");
            }
            //复制
            IOUtils.copy(is, os);
            os.flush();
        } catch (IOException e) {
            throw new BizException("下载文件失败！");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @DeleteMapping("/deleteFile/")
    public RespBean deleteFile(int urid) {
        if (urid == 0) {
            throw new BizException("缺少参数！");
        }
        userFileService.deleteFile(urid);
        return RespBean.ok("删除成功！");
    }

    /***
     * 获取项目所有文件包括招标的和投标的
     */
    @GetMapping("/getProjectFile")
    public RespPage getProjectFile(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, int projectid) {
        page = page - 1;
        return userFileService.findByProjectId(projectid, page, size);
    }
}
