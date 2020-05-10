package com.company.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface TorrentDownloader {
    void download(String torrentPath, String destinationPath) throws IOException, NoSuchAlgorithmException;
}
