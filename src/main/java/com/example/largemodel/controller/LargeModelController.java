package com.example.largemodel.controller;


import com.example.largemodel.exception.BusinessException;
import com.example.largemodel.request.AnalysisFileRequest;
import com.example.largemodel.respone.Result;
import com.example.largemodel.service.LargeModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("")
public class LargeModelController {

    @Autowired
    LargeModelService largeModelService;

    @PostMapping("/analysisFile")
    public Result analysisFile(@RequestParam("file") MultipartFile file,
                               @RequestParam("analysisContent") String analysisContent){
        if(ObjectUtils.isEmpty(file)) return Result.FAIL(001, "需要解析的文档不能为空");
        if(StringUtils.isEmpty(analysisContent)) return Result.FAIL(001, "文档总结不能为空");
        if (!(file.getOriginalFilename().endsWith(".pdf") || file.getOriginalFilename().endsWith(".doc")
                || file.getOriginalFilename().endsWith(".docx"))){
            return Result.FAIL(001, "导入文件格式错误，请导入word/pdf格式的文件！");
        }
        AnalysisFileRequest analysisFileRequest = new AnalysisFileRequest();
        analysisFileRequest.setFile(transferToFile(file));
        analysisFileRequest.setAnalysisContent(analysisContent);
        return Result.SUCCESS(largeModelService.analysisFile(analysisFileRequest));
    }

    private File transferToFile(MultipartFile multipartFile) {
      /*  // 选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], filename[1] + ".");
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;*/
        File file = null;
        try {
            file = File.createTempFile(multipartFile.getOriginalFilename(), null);
            FileCopyUtils.copy(multipartFile.getBytes(), file);
        } catch (IOException e) {
            throw BusinessException.CUSTOMER_BUSINESS("文件转换报错:" + e.getMessage());
        }
        return file;
    }


}
