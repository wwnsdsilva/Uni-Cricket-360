package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    UserDTO saveUser(UserDTO dto);

    UserDTO updateUser(Long id, UserDTO dto);

    UserDTO patchUser(Long id, UserDTO dto);

    void deleteUser(Long id);
}
