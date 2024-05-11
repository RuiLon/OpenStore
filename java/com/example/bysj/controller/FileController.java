package com.example.bysj.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.bysj.constants.urlConstants;
import com.example.bysj.enity.Files;
import com.example.bysj.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("files")
public class FileController {

    @Value("${files.upload.path}")
    private String filesUploadPath;

    @Autowired
    private FilesMapper filesMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //存入磁盘
        File uploadParentFile = new File(filesUploadPath);
        //判断目录是否存在，不存在则新建
        if(!uploadParentFile.exists())
        {
            uploadParentFile.mkdirs();
        }
        //文件唯一标识码
        String urlid = IdUtil.fastSimpleUUID();
        String Urlid = urlid +StrUtil.DOT + type;
        File uploadFile = new File(filesUploadPath + Urlid);

        String url;


        file.transferTo(uploadFile);

        String md5 = SecureUtil.md5(uploadFile);



        Files dbFiles = this.readOneByMd5(md5);


        if (!ObjectUtils.isEmpty(dbFiles))
        {
            url = dbFiles.getUrl();
            uploadFile.delete();
        }
        else
        {
            url = "http://localhost:8080/files/" + Urlid;
        }

        //存储数据库
        Files files = new Files();
        files.setFname(originalFilename);
        files.setSize(size/1024);
        files.setType(type);
        files.setUrl(url);
        files.setMd5(md5);
        this.filesMapper.insert(files);
        return url;
    }

    @GetMapping("/{Urlid}")
    public void download(@PathVariable String Urlid, HttpServletResponse response) throws IOException
    {
        File uploadFile = new File(filesUploadPath + Urlid);
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(Urlid,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取上传字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }


    //封装Md5方法
    private Files readOneByMd5(String md5)
    {
        if (!CollectionUtils.isEmpty(this.filesMapper.readAllByMd5(md5))) {
            return this.filesMapper.readAllByMd5(md5).get(0);
        }
        else {
            return null;
        }
    }
}
