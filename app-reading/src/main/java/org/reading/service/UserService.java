package org.reading.service;

import io.vertx.ext.auth.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.reading.entity.UserEntity;
import org.reading.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        UserEntity.persist(userEntity);
        return userEntity;
    }

    public List<UserEntity> findAll(Integer page, Integer pageSize) {
        return UserEntity.findAll()
                .page(page, pageSize)
                .list();
    }

    public List<UserEntity> findAllUsers() {
        return UserEntity.findAll()
                .list();
    }


    public UserEntity findByID(UUID userID) {
        return (UserEntity) UserEntity.findByIdOptional(userID)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity updateUser(UUID userID, UserEntity userEntity) {
        var user = findByID(userID);

        user.username = userEntity.username;

        UserEntity.persist(user);

        return user;
    }

    public void deleteUser(UUID userID) {
        var user = findByID(userID);
        UserEntity.deleteById(user.id);
    }
}
