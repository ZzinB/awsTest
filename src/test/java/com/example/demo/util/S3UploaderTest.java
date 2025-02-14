package com.example.demo.util;

import ch.qos.logback.core.encoder.EchoEncoder;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2

class S3UploaderTest {
    @Autowired private S3Uploader s3Uploader;

    @Test
    public void testUpload(){
        try {
            String filePath = "/Users/shinbi/upload/test.png";

            String uploadName = s3Uploader.upload(filePath);

            log.info(uploadName);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Test
    public void testRemove(){
        try {
            s3Uploader.removeS3File("test.png");
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}