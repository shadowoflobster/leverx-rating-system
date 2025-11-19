package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.application.ports.User.SaveUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private SaveUserPort saveUserPort;

    public void registerUser(UserRequest request){
        saveUserPort.save(request);
    }
}
