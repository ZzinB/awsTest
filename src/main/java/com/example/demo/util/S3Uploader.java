package com.example.demo.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
@Log4j2
public class S3Uploader {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket; //S3버킷이름

    //파일 업로드
    public String upload(String filePath) throws RuntimeException{
        File targetFile = new File(filePath);

        String uploadImageUrl = putS3(targetFile, targetFile.getName()); //s3로 업로드

        removeOriginalFile(targetFile);
        return uploadImageUrl;
    }

    //s3로 업로드
    private String putS3(File uploadFile, String fileName) throws RuntimeException{
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    //업로드 후 원본 파일 삭제
    private void removeOriginalFile(File targetFile){
        if (targetFile.exists() && targetFile.delete()){
            log.info("File delete success");
            return;
        }
        log.info("Fail to remove");
    }

    public void removeS3File(String fileName){
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}
