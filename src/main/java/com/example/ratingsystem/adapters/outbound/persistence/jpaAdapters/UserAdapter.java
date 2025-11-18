package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.UserMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.User.LoadUserPort;
import com.example.ratingsystem.application.ports.User.SaveUserPort;
import com.example.ratingsystem.domain.models.User;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserAdapter implements LoadUserPort, SaveUserPort {
    private JpaUserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public Optional<User> loadById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::entityToDomain);
    }

    @Override
    public List<User> loadByFullName(String fullName) {
        return userRepository.findByFullName(fullName)
                .stream()
                .map(userMapper::entityToDomain)
                .toList();
    }

    @Override
    public User save(UserRequest userRequest) {
        if (userRequest == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        UserEntity entity = userMapper.requestToEntity(userRequest);
        UserEntity savedEntity = userRepository.save(entity);

        return userMapper.entityToDomain(savedEntity);
    }




}
