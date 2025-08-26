package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.AdminDTO;
import com.nsbm.uni_cricket_360.dto.CoachDTO;
import com.nsbm.uni_cricket_360.dto.PlayerDTO;
import com.nsbm.uni_cricket_360.dto.UserDTO;
import com.nsbm.uni_cricket_360.entity.Admin;
import com.nsbm.uni_cricket_360.entity.Coach;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.entity.User;
import com.nsbm.uni_cricket_360.exception.InvalidCredentialsException;
import com.nsbm.uni_cricket_360.exception.InvalidRoleException;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.UserRepo;
import com.nsbm.uni_cricket_360.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${admin.registration.secret}")
    private String adminSecret;

    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
        return mapToDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email " + email));
        return mapToDTO(user);
    }

    @Override
    public UserDTO saveUser(UserDTO dto) {
        System.out.println("------------------- Inside UserServiceImpl: saveUser -------------------");
        System.out.println(dto);
        System.out.println(dto.getUser_role());

        // Check admin key if role is ADMIN
        if ("ADMIN".equalsIgnoreCase(dto.getUser_role())) {
            if (dto.getAdmin_key() == null || !dto.getAdmin_key().equals(adminSecret)) {
                throw new InvalidCredentialsException("Invalid admin registration key!");
            }
        }

        User user;

        // Pick correct subclass
        String role = dto.getUser_role().toUpperCase();

        switch (role) {
            case "ADMIN":
                user = mapper.map(dto, Admin.class);
                break;
            case "COACH":
                user = mapper.map(dto, Coach.class);
                break;
            case "PLAYER":
                user = mapper.map(dto, Player.class);
                break;
            default:
                throw new InvalidRoleException("Invalid user role: " + role);
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
        return mapToDTO(saved);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));

        // check admin secret if new role = ADMIN
        if ("ADMIN".equalsIgnoreCase(dto.getUser_role())) {
            if (dto.getAdmin_key() == null || !dto.getAdmin_key().equals(adminSecret)) {
                throw new InvalidCredentialsException("Invalid admin registration key!");
            }
        }

        // Update fields
        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        existing.setUser_role(dto.getUser_role().toUpperCase());

        User updated = userRepo.save(existing);

        return mapToDTO(updated);
    }

    // Update as required
    @Override
    public UserDTO patchUser(Long id, UserDTO dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));

        if ("ADMIN".equalsIgnoreCase(dto.getUser_role())) {
            throw new InvalidCredentialsException("Cannot upgrade user to ADMIN without admin key!");
        }

        user.setUser_role(dto.getUser_role().toUpperCase());
        User updated = userRepo.save(user);

        return mapToDTO(updated);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
        userRepo.delete(user);
    }

    private UserDTO mapToDTO(User user) {
        if (user instanceof Admin) {
            return mapper.map(user, AdminDTO.class);
        } else if (user instanceof Coach) {
            return mapper.map(user, CoachDTO.class);
        } else if (user instanceof Player) {
            return mapper.map(user, PlayerDTO.class);
        }
        return mapper.map(user, UserDTO.class);
    }
}
