package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.LoginRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.adapters.inbound.security.JwtUtils;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.UserMapper;
import com.example.ratingsystem.application.ports.User.LoadUserPort;
import com.example.ratingsystem.application.ports.User.PasswordResetPort;
import com.example.ratingsystem.application.ports.User.SaveUserPort;
import com.example.ratingsystem.application.ports.User.VerifyUserPort;
import com.example.ratingsystem.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SaveUserPort saveUserPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final LoadUserPort loadUserPort;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final VerifyUserPort verifyUserPort;
    private final PasswordResetPort passwordResetPort;


    public String login(LoginRequest request) {
        User user = loadUserPort.loadByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        } else if (!user.isApproved()) {
            throw new DisabledException("User account is not approved by admin yet");
        } else if (!user.isVerified()) {
            throw new DisabledException("User account is not verified yet, Check email");
        }

        return jwtUtils.generateToken(user);
    }

    public User registerUser(UserRequest request) {

        User user = userMapper.requestToDomain(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return saveUserPort.save(user);

    }

    public boolean verifyUser(String email, String code) {
        boolean verified = emailService.verifyCode(email, code);
        if (verified) {
            User user = loadUserPort.loadByEmail(email);
            if (user == null) {
                throw new IllegalArgumentException("No user found with email: " + email);
            }
            user.setVerified(true);
            verifyUserPort.verify(user);
            return true;
        }
        return false;
    }

    public void resetPasswordRequest(String email) {
        emailService.sendVerificationEmail(email);
    }

    public boolean resetPassword(String email, String code, String password) {
        boolean verified = emailService.verifyCode(email, code);
        if (verified) {
            User user = loadUserPort.loadByEmail(email);
            if (user == null) {
                throw new IllegalArgumentException("No user found with email: " + email);
            }
            user.setPassword(passwordEncoder.encode(password));
            passwordResetPort.resetPassword(user);
            return true;
        }
        return false;
    }
}
