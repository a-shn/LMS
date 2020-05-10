package com.company.services.torrent;

import com.company.repositories.CourseLessonsRepository;
import com.company.services.CourseBuilder;
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
    private CourseLessonsRepository courseLessonsRepository;

    public TorrentDownloaderImpl() {
    }

    @Override
    public void download(String torrentPath, String destinationPath) throws IOException, NoSuchAlgorithmException {
        TorrentThread torrentThread = new TorrentThread(torrentPath, destinationPath);
        torrentThread.start();
    }

    private static class TorrentThread extends Thread {
        private String torrentPath;
        private String destinationPath;

        TorrentThread(String torrentPath, String destinationPath) {
            this.torrentPath = torrentPath;
            this.destinationPath = destinationPath;
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
//            int lessonsNumber = courseBuilder.build(destinationPath);
//            String[] tmp = destinationPath.split("/");
//            int courseId = Integer.parseInt(tmp[tmp.length - 2]);
//            courseLessonsRepository.addLessonsOfCourse(courseId, lessonsNumber);
        }
    }

}
