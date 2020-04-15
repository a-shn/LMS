package com.company.repositories;

import com.company.dto.UserDto;
import com.company.models.User;

import java.util.Optional;

public interface UsersRepository {
    void save(UserDto userDto);

    Optional<User> findByEmail(String email);
}
