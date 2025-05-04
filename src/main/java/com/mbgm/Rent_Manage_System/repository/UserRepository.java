package com.mbgm.Rent_Manage_System.repository;

import com.mbgm.Rent_Manage_System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);


}
