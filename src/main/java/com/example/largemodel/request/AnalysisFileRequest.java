package com.example.largemodel.request;

import lombok.Data;

import java.io.File;

@Data
public class AnalysisFileRequest {

    private File file;

    private String analysisContent;

}
