package io.github.zhc.dev.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhc-dev
 * @data 2025/4/16 21:05
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.oss")
public class OSSProperties {
    private String endpoint;

    private String region;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    /**
     * 路径前缀，加在 endPoint 之后 (bucket 目录)
     */
    private String pathPrefix;  // horizon
}
