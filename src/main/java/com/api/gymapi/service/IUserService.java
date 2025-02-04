package com.api.gymapi.service;

import com.api.gymapi.models.User;

public interface IUserService {
    public User getUser(long id);
    public User getUserByUsername(String username);
    public User createUser(User user);
    public User updateUser(User user);
    public User deleteUser(long id);
}
