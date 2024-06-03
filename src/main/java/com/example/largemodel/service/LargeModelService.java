package com.example.largemodel.service;

import com.alibaba.fastjson.JSONArray;
import com.example.largemodel.request.AnalysisFileRequest;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import com.alibaba.fastjson.JSONObject;


@Service("largeModelService")
public class LargeModelService extends WebSocketListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${modelScope_api.api_key}")
    private String apiKey;

    @Value("${modelScope_api.model}")
    private String model;

    @Value("${modelScope_api.purpose}")
    private String purpose;

    @Autowired
    private OkHttpClient client;

    private String uploadFileToAli(AnalysisFileRequest analysisFileRequest){
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Content-type","multipart/form-data")
                .addFormDataPart("file", analysisFileRequest.getFile().getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                analysisFileRequest.getFile()))
                .addFormDataPart("purpose",purpose)
                .build();
        Request request = new Request.Builder()
                .url("https://dashscope.aliyuncs.com/compatible-mode/v1/files")
                .method("POST", body)
                .addHeader("Authorization", apiKey)
                .build();
        String fileId = null;
        try (Response response = client.newCall(request).execute()){
            JSONObject jsonObject = JSONObject.parseObject(response.body().string());
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 上传文档至阿里返参数：{}", jsonObject);
            if ("processed".equals(jsonObject.getString("status"))) {
                // 请求成功
                fileId = jsonObject.getString("id");
            }
        } catch (Exception e) {
            logger.error("上传文档至阿里失败：{}", e);
        } finally {
            // 保存请求接口参数日志
        }
        return fileId;
    }

    public String analysisFile(AnalysisFileRequest analysisFileRequest){
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"model\": \"" + model +"\"," +
                "\r\n    \"messages\": [\r\n     \r\n        {\r\n            \"role\": \"system\"," +
                "\r\n            \"content\": \"fileid://"+ uploadFileToAli(analysisFileRequest) +"\"\r\n        }," +
                "\r\n        {\r\n            \"role\": \"user\"," +
                "\r\n            \"content\": \""+ analysisFileRequest.getAnalysisContent() +"\"\r\n        }\r\n    ]\r\n}");
        Request request = new Request.Builder()
                .url("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions")
                .method("POST", body)
                .addHeader("Authorization", apiKey)
                .addHeader("Content-Type", "application/json")
                .build();
        String respone = null;
        try (Response response = client.newCall(request).execute()){
            JSONObject jsonObject = JSONObject.parseObject(response.body().string());
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>解析文档返参数：{}", jsonObject);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject jsonObject1 = choices.getJSONObject(0);
            JSONObject message = jsonObject1.getJSONObject("message");
            respone = message.getString("content");
        } catch (Exception e) {
            logger.error("解析文档失败：{}", e);
        } finally {
            // 保存请求接口参数日志
        }
        return respone;
    }

}
