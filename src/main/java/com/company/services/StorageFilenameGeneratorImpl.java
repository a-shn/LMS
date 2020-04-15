package com.company.services;

import com.company.repositories.FileRepository;

import java.util.UUID;

public class StorageFilenameGeneratorImpl implements StorageFilenameGenerator {
    FileRepository fileRepository;

    public StorageFilenameGeneratorImpl() {
    }

    public String generateStorageFilename() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
