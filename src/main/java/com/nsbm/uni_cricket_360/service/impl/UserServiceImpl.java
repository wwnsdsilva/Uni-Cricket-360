package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>() {}.getType());
    }
}
