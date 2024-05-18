package com.example.instagram.api.using.springboot.service;

import com.example.instagram.api.using.springboot.dto.UserDto;
import com.example.instagram.api.using.springboot.exceptions.UserException;
import com.example.instagram.api.using.springboot.model.User;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public interface UserService{
    public User registeredUser(User user) throws UserException;
    public User findUserById(Integer userId) throws UserException;
    public User findUserProfile(String token) throws UserException;
    public User findUserByUsername(String username) throws UserException;
    public String followerUser(Integer reqUserId,Integer followUserId) throws UserException;
    public String unFollowUser(Integer reqUserId,Integer unfollowUserId) throws UserException;
    public List<User> findUsersByUserIds(List<Integer> userIds);
    public List<User> searchUser(String query) throws UserException;
    public List<User> popularUser();
    public User updateUserDetails(User updateUser,User existingUser) throws UserException;

    String followUser(Integer reqUserId, Integer followUserId) throws UserException;

    String unfollowUser(Integer id, Integer unfollowUserId) throws UserException;

    User createUserById(UserDto userDto);
}
