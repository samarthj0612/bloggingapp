package com.samdb.bloggingapp.repositories;

import com.samdb.bloggingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
