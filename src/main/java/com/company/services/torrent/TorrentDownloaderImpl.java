package com.company.services.torrent;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;


public class TorrentDownloaderImpl implements TorrentDownloader {
    public TorrentDownloaderImpl() {
    }

    @Override
    public void download(String torrentPath, String destinationPath) throws IOException, NoSuchAlgorithmException {
        Client client = new Client(
                InetAddress.getLocalHost(),
                SharedTorrent.fromFile(
                        new File(torrentPath),
                        new File(destinationPath)));
        client.download();
        client.waitForCompletion();
    }

}
