package com.example.largemodel;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class LargeModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(LargeModelApplication.class, args);
    }


    @Bean
    public OkHttpClient okHttpClient() {

        ConnectionPool connectionPool = new ConnectionPool(5, 10, TimeUnit.HOURS);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .build();
        return client;
    }

}
