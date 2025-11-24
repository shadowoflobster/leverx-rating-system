package com.example.ratingsystem.application.services;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender mailSender;
    private RedisTemplate<String, String> redisTemplate;

    public String generate6DigitCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendVerificationEmail(String email) {
        String code = generate6DigitCode();

        redisTemplate.opsForValue().set(email, code, 24, TimeUnit.HOURS);

        String subject = "Confirm your email";
        String body = "Your verification code is: (" + code + ") It will expire in 24 hours.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }

    public boolean verifyCode(String email, String code) {
        String cachedCode = redisTemplate.opsForValue().get(email);
        if (cachedCode != null && cachedCode.equals(code)) {
            redisTemplate.delete(email);
            return true;
        }
        return false;
    }


}
