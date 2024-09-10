package com.demo.taskproject.serviceImpl;

import com.demo.taskproject.entity.Users;
import com.demo.taskproject.payload.UserDto;
import com.demo.taskproject.repository.UserRepository;
import com.demo.taskproject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        //userDto is not an entity of Users
        // converted userDto to Users
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users user = userDtoToEntity(userDto);
        Users savedUser = userRepository.save(user);
        return entityToUserDto(savedUser);
    }
        //method to convert userDto to users entity

        private Users userDtoToEntity(UserDto userDto){
          Users users=new Users();
          users.setName(userDto.getName());
          users.setEmail(userDto.getEmail());
          users.setPassword(userDto.getPassword());
          return users;
        }
        //Method to convert UserDto to users entity
        private UserDto entityToUserDto(Users savedUser){
            UserDto userDto = new UserDto();
            userDto.setId(savedUser.getId());
            userDto.setEmail(savedUser.getEmail());
            userDto.setPassword(savedUser.getPassword());
            userDto.setName(savedUser.getName());
            return userDto;
        }

    }
