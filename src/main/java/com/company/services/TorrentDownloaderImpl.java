package com.company.services;

import com.company.repositories.CourseLessonsRepository;
import com.company.repositories.CoursesRepository;
import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

@Component
public class TorrentDownloaderImpl implements TorrentDownloader {
    @Autowired
    private CourseBuilder courseBuilder;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private CourseLessonsRepository courseLessonsRepository;

    public TorrentDownloaderImpl() {
    }

    @Override
    public void download(String torrentPath, String destinationPath) throws IOException, NoSuchAlgorithmException {
        TorrentThread torrentThread = new TorrentThread(torrentPath, destinationPath, courseBuilder, courseLessonsRepository,
                coursesRepository);
        torrentThread.start();
    }

    private static class TorrentThread extends Thread {
        private final String torrentPath;
        private final String destinationPath;
        private final CourseBuilder courseBuilder;
        private final CourseLessonsRepository courseLessonsRepository;
        private final CoursesRepository coursesRepository;

        TorrentThread(String torrentPath, String destinationPath,CourseBuilder courseBuilder,
                      CourseLessonsRepository courseLessonsRepository, CoursesRepository coursesRepository) {
            this.torrentPath = torrentPath;
            this.destinationPath = destinationPath;
            this.courseBuilder = courseBuilder;
            this.courseLessonsRepository = courseLessonsRepository;
            this.coursesRepository = coursesRepository;
        }

        @SneakyThrows
        @Override
        public void run() {
            Client client = new Client(
                    InetAddress.getLocalHost(),
                    SharedTorrent.fromFile(
                            new File(torrentPath),
                            new File(destinationPath)));
            client.download();

            client.waitForCompletion();

            String[] tmp = destinationPath.split("/");
            int courseId = Integer.parseInt(tmp[tmp.length - 2]);
            coursesRepository.updateStatus(courseId, "AVAILABLE");
            int lessonsNumber = courseBuilder.build(destinationPath);
            courseLessonsRepository.addLessonsOfCourse(courseId, lessonsNumber);
        }
    }

}
