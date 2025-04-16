package io.github.zhc.dev.friend.controller.file;

import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.file.model.entity.OSSResult;
import io.github.zhc.dev.friend.service.file.FileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhc-dev
 * @data 2025/4/16 21:19
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public R<OSSResult> upload(@RequestBody MultipartFile file) {
        return R.ok(fileService.upload(file));
    }
}
