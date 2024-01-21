package com.test.tester.hexagonal.framework.adapter.driven;

import com.test.tester.hexagonal.application.port.output.FileReaderOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LocalFileReaderAdapter implements FileReaderOutputPort {
    private final String filePath;

    public LocalFileReaderAdapter(@Value("${file.path}") String filePath) {
        this.filePath = filePath;
    }
    @Override
    public String read() {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }
}