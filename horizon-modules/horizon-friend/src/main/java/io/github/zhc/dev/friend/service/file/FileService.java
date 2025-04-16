package io.github.zhc.dev.friend.service.file;

import io.github.zhc.dev.file.model.entity.OSSResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhc-dev
 * @data 2025/4/16 21:21
 */
public interface FileService {
    OSSResult upload(MultipartFile file);
}
