package com.company.services.torrent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface TorrentDownloader {
    void download(String torrentPath, String destinationPath) throws IOException, NoSuchAlgorithmException;
}
