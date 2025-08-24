package com.nsbm.uni_cricket_360.repository;

import com.nsbm.uni_cricket_360.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
