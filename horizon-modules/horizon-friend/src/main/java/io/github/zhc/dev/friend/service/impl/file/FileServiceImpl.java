package io.github.zhc.dev.friend.service.impl.file;

import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.file.model.entity.OSSResult;
import io.github.zhc.dev.file.service.OSSService;
import io.github.zhc.dev.friend.service.file.FileService;
import io.github.zhc.dev.security.exception.ServiceException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhc-dev
 * @data 2025/4/16 21:21
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Resource
    private OSSService ossService;

    @Override
    public OSSResult upload(MultipartFile file) {
        try {
            return ossService.uploadFile(file);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(ResultCode.FAILED_FILE_UPLOAD);
        }
    }
}
