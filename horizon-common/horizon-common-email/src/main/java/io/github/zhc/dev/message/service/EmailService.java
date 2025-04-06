package io.github.zhc.dev.message.service;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author zhc.dev
 * @date 2025/4/6 16:02
 */
@Service
public class EmailService {
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送验证码邮件
     *
     * @param to 收件人邮箱
     * @return 发送结果
     */
    public boolean sendVerificationCode(String verificationCode, String to) {
        try {
            // 将验证码转为字符数组以便在模板中遍历
            char[] verifyCodeArray = verificationCode.toCharArray();

            // 创建Thymeleaf上下文
            Context context = new Context();
            context.setVariable("verifyCode", verifyCodeArray);

            // 使用模板引擎处理模板
            String emailContent = templateEngine.process("email-verification", context);

            // 创建邮件内容
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Horizon");
            helper.setText(emailContent, true);

            // 发送邮件
            mailSender.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
