package com.company.services;

import com.company.dto.FileDto;
import com.company.models.Course;
import com.company.models.FileInfo;
import com.company.repositories.CoursesRepository;
import com.company.repositories.FileRepository;
import com.company.services.torrent.TorrentDownloader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class FileUploaderImpl implements FileUploader {
    private String directory;
    private FileRepository fileRepository;
    private CoursesRepository coursesRepository;
    private StorageFilenameGenerator storageFilenameGenerator;
    private TorrentDownloader torrentDownloader;

    public FileUploaderImpl(String directory, FileRepository fileRepository, CoursesRepository coursesRepository,
                            StorageFilenameGenerator storageFilenameGenerator, TorrentDownloader torrentDownloader) {
        this.directory = directory;
        this.fileRepository = fileRepository;
        this.coursesRepository = coursesRepository;
        this.storageFilenameGenerator = storageFilenameGenerator;
        this.torrentDownloader = torrentDownloader;
    }

    @Override
    public FileDto uploadAndSaveToDb(MultipartFile multipartFile) {
        try {
            String type = multipartFile.getContentType();
            Long size = multipartFile.getSize();
            String storageFilename = storageFilenameGenerator.generateStorageFilename();
            String path = directory + "/" + storageFilename + "/" + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(path));
            fileRepository.save(new FileInfo(storageFilename, multipartFile.getOriginalFilename(), path, size, type));
            return new FileDto("sh.il.almaz@gmail.com", "anonymous", "localhost:8080/getfile/" + storageFilename, storageFilename, multipartFile.getOriginalFilename(), size, type);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public void uploadCourse(MultipartFile multipartFile) {
        try {
            String type = multipartFile.getContentType();
            Long size = multipartFile.getSize();
            Course course = new Course(null, null, null,
                    multipartFile.getOriginalFilename());
            course = coursesRepository.save(course);
            String torrentPath = directory + "/coursesFolder/" + course.getCourseId() + "/torrent/" + course.getCourseId() + ".torrent";
            String coursePath = directory + "/coursesFolder/" + course.getCourseId() + "/course/";
            File file = new File(coursePath);
            multipartFile.transferTo(new File(torrentPath));
            fileRepository.save(new FileInfo(course.getCourseId().toString(), multipartFile.getOriginalFilename(), torrentPath, size, type));
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                torrentDownloader.download(torrentPath, coursePath);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
