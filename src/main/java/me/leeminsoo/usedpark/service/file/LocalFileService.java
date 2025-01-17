package me.leeminsoo.usedpark.service.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocalFileService {
    @Value("${file.profileImagePath}")
    private String uploadFolder;
}
