package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.UserMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.User.*;
import com.example.ratingsystem.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserAdapter implements LoadUserPort, SaveUserPort, DeleteUserPort, VerifyUserPort, PasswordResetPort {
    private JpaUserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public List<User> loadAll() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToDomain)
                .toList();
    }

    @Override
    public Optional<User> loadById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::entityToDomain);
    }

    @Override
    public UserEntity loadEntityById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email"));
    }

    @Override
    public List<User> loadByFullName(String fullName) {

        String searchPattern = "%" + fullName.trim() + "%";
        return userRepository.findByFullName(fullName)
                .stream()
                .map(userMapper::entityToDomain)
                .toList();
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        UserEntity entity;

        if (user.getId() != null) {
            entity = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            entity.setApproved(user.isApproved());
        } else {
            entity = userMapper.domainToEntity(user);
        }
        UserEntity savedEntity = userRepository.save(entity);

        return userMapper.entityToDomain(savedEntity);
    }

    @Override
    public User loadByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email"));

        return userMapper.entityToDomain(user);

    }

    @Override
    public UserEntity loadEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email"));
    }

    @Override
    public List<User> loadPendingUsers() {
        return userRepository.findByApprovedFalse().stream()
                .map(userMapper::entityToDomain)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void verify(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or User ID cannot be null for update");
        }

        UserEntity entity = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + user.getId()));

        entity.setVerified(user.isVerified());

        UserEntity savedEntity = userRepository.save(entity);
        userMapper.entityToDomain(savedEntity);
    }

    @Override
    public void resetPassword(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or User ID cannot be null for update");
        }

        UserEntity entity = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + user.getId()));

        entity.setPassword(user.getPassword());

        UserEntity savedEntity = userRepository.save(entity);
        userMapper.entityToDomain(savedEntity);
    }

}
