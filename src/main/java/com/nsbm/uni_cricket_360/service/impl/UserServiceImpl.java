package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.AdminDTO;
import com.nsbm.uni_cricket_360.dto.CoachDTO;
import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.entity.Admin;
import com.nsbm.uni_cricket_360.entity.Coach;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.User;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public UserDTO saveUser(UserDTO dto) {
        System.out.println("------------------- Inside UserServiceImpl: saveUser -------------------");
        System.out.println(dto);
        System.out.println(dto.getUser_role());

        User user;

        // Pick correct subclass
        switch (dto.getUser_role().toUpperCase()) {
            case "ADMIN":
                user = mapper.map(dto, Admin.class);
                break;
            case "COACH":
                user = mapper.map(dto, Coach.class);
                break;
            case "PLAYER":
                user = mapper.map(dto, Player.class);
                break;
            case "":
                user = mapper.map(dto, User.class);
                break;
            default:
                throw new IllegalArgumentException("Invalid user role: " + dto.getUser_role());
        }

        System.out.println("user before saving");
        System.out.println(user);

        //Hash password before saving
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Save entity
        User saved = userRepo.save(user);

        System.out.println("user after saving");
        System.out.println(saved);

        // Map back to role-specific DTO
        if (saved instanceof Admin) {
            return mapper.map(saved, AdminDTO.class);
        } else if (saved instanceof Coach) {
            return mapper.map(saved, CoachDTO.class);
        } else if (saved instanceof Player) {
            return mapper.map(saved, PlayerDTO.class);
        }

        return mapper.map(saved, UserDTO.class);
    }
}
