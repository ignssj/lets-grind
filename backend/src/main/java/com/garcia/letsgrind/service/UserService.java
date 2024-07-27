package com.garcia.letsgrind.service;

import com.garcia.letsgrind.domain.user.UpdateUserDTO;
import com.garcia.letsgrind.domain.user.User;
import com.garcia.letsgrind.domain.user.CreateUserDTO;
import com.garcia.letsgrind.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UUID createUser(CreateUserDTO data){
        User entity = new User(
                UUID.randomUUID(),
                data.email(),
                data.password()
        );

        var savedUser = userRepository.save(entity);
        return savedUser.getId();
    }

    public Optional<User> getUserById(String id){
        return this.userRepository.findById(UUID.fromString(id));
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public Optional<User> updateUserById(String id, UpdateUserDTO data){
        var userEntity = this.userRepository.findById(UUID.fromString(id));

        if (userEntity.isEmpty()){
            return userEntity;
        }

        var user = userEntity.get();

        if (data.email() != null){
            user.setEmail(data.email());
        }
        if (data.password() != null){
            user.setPassword(data.password());
        }

        this.userRepository.save(user);
        return this.userRepository.findById(UUID.fromString(id));
    }
    public void deleteById(String id){
        var uuid = UUID.fromString(id);

        if(this.userRepository.existsById(uuid)) {
            this.userRepository.deleteById(uuid);
        }
    }


}
