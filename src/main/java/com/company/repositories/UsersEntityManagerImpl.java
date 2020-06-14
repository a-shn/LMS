package com.company.repositories;

import com.company.dto.UserDto;
import com.company.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class UsersEntityManagerImpl implements UsersRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        try {
            User user = (User) entityManager.createQuery("SELECT user FROM User user WHERE user.email=?1")
                    .setParameter(1, email)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
