package com.garcia.letsgrind.controller;

import com.garcia.letsgrind.domain.user.UpdateUserDTO;
import com.garcia.letsgrind.domain.user.User;
import com.garcia.letsgrind.domain.user.CreateUserDTO;
import com.garcia.letsgrind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserDTO body){
        var id = this.userService.createUser(body);
        return ResponseEntity.created(URI.create("users/"+id.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String id){
        var user = this.userService.getUserById(id);
        if (user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        var users = this.userService.getAllUsers();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<User>> updateById(@PathVariable("id") String id,
                                                     @RequestBody UpdateUserDTO body){
        var user = this.userService.updateUserById(id, body);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id){
        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
