package io.github.zhc.dev.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:35
 */
@Slf4j
@Configuration
public class XxlJobConfig {
    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.appname}")
    private String appname;
    
    @Value("${xxl.job.executor.logpath:/Users/zhc.dev/data/applogs/xxl-job/jobhandler}")
    private String logPath;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        
        // 确保日志目录存在
        File logPathDir = new File(logPath);
        if (!logPathDir.exists()) {
            boolean result = logPathDir.mkdirs();
            log.info("创建XXL-JOB日志目录 {} 结果: {}", logPath, result ? "成功" : "失败");
        }
        
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        
        log.info("XXL-JOB日志目录设置为: {}", logPath);
        return xxlJobSpringExecutor;
    }
}
