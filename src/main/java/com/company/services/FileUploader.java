package com.company.services;

import com.company.dto.FileDto;
import com.company.models.Course;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    FileDto uploadAndSaveToDb(MultipartFile multipartFile);
    void uploadCourse(MultipartFile multipartFile, Course course);
}
