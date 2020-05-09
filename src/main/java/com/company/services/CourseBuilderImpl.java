package com.company.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseBuilderImpl implements CourseBuilder {
    @Override
    public int build(String directory) {
        try {
            return buildFileStructure(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int buildFileStructure(String directory) throws IOException {
        Stream<Path> walk = Files.walk(Paths.get(directory));
        List<String> files = walk.filter(Files::isRegularFile)
                .map(x -> x.toString()).collect(Collectors.toList());

        List<String> videos = new ArrayList<>();
        List<String> other = new ArrayList<>();
        for (String file : files) {
            Path tmp = new File(file).toPath();
            if (Files.probeContentType(tmp).split("/")[0].equals("video")) {
                videos.add(file);
            } else {
                other.add(file);
            }
        }
        Collections.sort(videos);
        for (int i = 1; i < videos.size() + 1; i++) {
            String tmp = directory + "/lesson_" + i;
            File file = new File(tmp);
            if (!file.exists()) {
                file.mkdir();
            }
        }
        for (int i = 1; i < videos.size() + 1; i++) {
            String[] tmp = videos.get(i - 1).split("/");
            String newPath = directory + "/lesson_" + i + "/" + videos.get(i - 1).split("/")[tmp.length - 1];
            Files.move(Paths.get(videos.get(i - 1)), Paths.get(newPath));
        }
        File otherFolder = new File(directory + "/other");
        if (!otherFolder.exists()) {
            otherFolder.mkdir();
        }
        for (String otherFile: other) {
            String[] tmp = otherFile.split("/");
            String newPath = directory + "/other/" + otherFile.split("/")[tmp.length - 1];
            Files.move(Paths.get(otherFile), Paths.get(newPath));
        }
        return videos.size();
    }
}
