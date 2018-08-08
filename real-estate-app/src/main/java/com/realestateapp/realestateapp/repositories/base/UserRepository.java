package com.realestateapp.realestateapp.repositories.base;

import com.realestateapp.realestateapp.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User getById(int id);

    boolean create(User user);

    boolean update(User user);

    boolean deleteById(int id);
}