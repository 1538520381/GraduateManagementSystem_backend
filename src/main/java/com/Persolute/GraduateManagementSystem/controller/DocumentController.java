package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.po.Document;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.DocumentService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/02/12 11:05
 */
@RestController
@RequestMapping("/document")
public class DocumentController {
    @Value("${GraduateManagementSystem.document.path}")
    private String documentPath;

    @Autowired
    private DocumentService documentService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 上传
     * @email 1538520381@qq.com
     * @date 2025/2/12 上午11:19
     */
    @PostMapping("/upload")
    public R upload(@RequestBody MultipartFile document) {
        String originalDocumentName = document.getOriginalFilename();
        String suffix = originalDocumentName.substring(originalDocumentName.lastIndexOf(".") + 1);

        Document newDocument = new Document();
        newDocument.setOriginalDocumentName(originalDocumentName);

        String documentPathName = UUID.randomUUID() + "." + suffix;
        newDocument.setDocumentPathName(documentPathName);

        File dir = new File(documentPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            document.transferTo(new File(documentPath + documentPathName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }

        return documentService.addDocument(newDocument);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 下载
     * @email 1538520381@qq.com
     * @date 2025/2/12 下午12:57
     */
    @GetMapping("/download/{id}")
    public void download(HttpServletResponse response, @PathVariable Long id) {
        Document document = documentService.getById(id);

        try {
            ServletOutputStream outputStream = response.getOutputStream();

            if (document == null) {
                response.setContentType("text/html;charset=utf-8");
                byte[] bytes = JSON.toJSONString(R.error("文件不存在")).getBytes(StandardCharsets.UTF_8);
                outputStream.write(bytes, 0, bytes.length);
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(documentPath + document.getDocumentPathName());

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(document.getOriginalDocumentName(), "UTF-8") + "\"");
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }
    }
}
