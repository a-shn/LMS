package com.company.services;

import com.company.dto.UserDto;
import com.company.models.User;
import com.company.repositories.UsersRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class SignUpPool {
    private Map<String, UserDto> signUpPool;
    private Map<String, LocalDateTime> linkCreationTimes;
    private Map<String, String> linksOfEmails;
    private UsersRepository usersRepository;

    public SignUpPool(UsersRepository usersRepository) {
        signUpPool = new HashMap<>();
        linkCreationTimes = new HashMap<>();
        linksOfEmails = new HashMap<>();
        this.usersRepository = usersRepository;
    }

    public void put(UserDto userDto, String link, LocalDateTime time) {
        deleteLinks(userDto.getEmail());
        signUpPool.put(link, userDto);
        linkCreationTimes.put(link, time);
        linksOfEmails.put(userDto.getEmail(), link);
    }

    public boolean verify(String link) {
        try {
            if (ChronoUnit.HOURS.between(linkCreationTimes.get(link), LocalDateTime.now()) > 24) {
                deleteLinks(signUpPool.get(link).getEmail());
                return false;
            } else {
                usersRepository.save(signUpPool.get(link));
                deleteLinks(signUpPool.get(link).getEmail());
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void deleteLinks(String email) {
        if (linksOfEmails.containsKey(email)) {
            signUpPool.remove(linksOfEmails.get(email));
            linkCreationTimes.remove(linksOfEmails.get(email));
            linksOfEmails.remove(email);
        }
    }
}
