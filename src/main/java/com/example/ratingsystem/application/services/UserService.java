package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.LoginRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.adapters.inbound.security.JwtUtils;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.UserMapper;
import com.example.ratingsystem.application.ports.User.LoadUserPort;
import com.example.ratingsystem.application.ports.User.SaveUserPort;
import com.example.ratingsystem.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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

    public String login(LoginRequest request) {
        User user = loadUserPort.loadByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtUtils.generateToken(user);
    }

    public User registerUser(UserRequest request) {

        User user = userMapper.requestToDomain(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return saveUserPort.save(user);

    }
}
