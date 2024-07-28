package com.garcia.letsgrind.service;

import com.garcia.letsgrind.domain.user.CreateUserDTO;
import com.garcia.letsgrind.domain.user.UpdateUserDTO;
import com.garcia.letsgrind.domain.user.User;
import com.garcia.letsgrind.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a user successfully")
        void shouldCreateUser(){

            var user = new User(
                    UUID.randomUUID(),
                    "superemail@gmail.com",
                    "superpassword"
            );

            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture()
                    );

            var input = new CreateUserDTO(
                    "superemail@gmail.com",
                    "superpassword"
            );

            var output = userService.createUser(input);
            var userCaptured = userArgumentCaptor.getValue();

            assertAll(
                    () -> assertNotNull(output),
                    () -> assertEquals(userCaptured.getEmail(), input.email()),
                    () -> assertEquals(userCaptured.getPassword(), input.password())
            );

        }

        @Test
        @DisplayName("Should throw an exception when creation fails")
        void shouldThrowAnExceptionWhenCreationFails(){

            doReturn(new RuntimeException())
                    .when(userRepository)
                    .save(any()
                    );

            var input = new CreateUserDTO(
                    "superemail@gmail.com",
                    "superpassword"
            );

            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should find and return a user when optional is present")
        void shouldFindAndReturnUser(){

            var user = new User(
                    UUID.randomUUID(),
                    "superemail@gmail.com",
                    "superpass"
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture()
                    );

            var output = userService.getUserById(user.getId().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should return null when optional is empty")
        void shouldReturnNullWhenUserNotFound(){

            var id = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture()
                    );

            var output = userService.getUserById(id.toString());

            assertTrue(output.isEmpty());
            assertEquals(id, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class getAllUsers {

        @Test
        @DisplayName("Should return a list with all users")
        void shouldReturnUserList(){

            var user = new User(
                    UUID.randomUUID(),
                    "email",
                    "password"
            );
            var userList = List.of(user);

            doReturn(userList).when(userRepository).findAll();
            var output = userService.getAllUsers();

            assertAll(
                    () -> assertNotNull(output),
                    () -> assertEquals(output.size(), userList.size())
            );
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should call delete by id when user exists")
        void shouldCallDeleteByIdWhenUserExists(){

            var id = UUID.randomUUID();
            doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());

            userService.deleteById(id.toString());
            var captured = uuidArgumentCaptor.getAllValues();

            assertAll(
                    () -> assertEquals(captured.get(0), id),
                    () -> assertEquals(captured.get(1), id)
            );

        }

        @Test
        @DisplayName("Should call not call delete by id when user does not exist")
        void shouldNotCallDeleteByIdWhenUserDoesNotExist(){

            var id = UUID.randomUUID();
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            userService.deleteById(id.toString());
            var captured = uuidArgumentCaptor.getValue();

            verify(userRepository, times(1)).existsById(captured);
            verify(userRepository, times(0)).deleteById(any());
        }
    }

    @Nested
    class updateUserById {

        @Test
        @DisplayName("Should early return null when user does not exist")
        void shouldEarlyReturnNullWhenUserDoesNotExist(){

            var id = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(id);

            var input = new UpdateUserDTO(
                    "updatedemail",
                    "updatedpass"
            );
            var output = userService.updateUserById(id.toString(), input);

            assertNull(output);
            verify(userRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("Should call update when user exists")
        void shouldUpdateUserWhenItExists(){

            var userEntity = new User(
                    UUID.randomUUID(),
                    "email",
                    "pass"
            );
            doReturn(Optional.of(userEntity))
                    .when(userRepository)
                    .findById(userEntity.getId()
                    );

            var input = new UpdateUserDTO(
                    "email",
                    "password"
            );

            userService.updateUserById(userEntity.getId().toString(), input);

            verify(userRepository, times(2)).findById(any());
            verify(userRepository, times(1)).save(any());
        }
    }
}