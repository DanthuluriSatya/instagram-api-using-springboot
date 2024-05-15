package com.example.instagram.api.using.springboot.service;

import com.example.instagram.api.using.springboot.dto.UserDto;
import com.example.instagram.api.using.springboot.exceptions.UserException;
import com.example.instagram.api.using.springboot.model.User;
import com.example.instagram.api.using.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository repo;

    @Override
    public User registeredUser(User user) throws UserException {
        System.out.println("registered user-----");

        Optional<User> isEmailExists=repo.findByEmail(user.getEmail());
        if(isEmailExists.isPresent())
        {
            throw new UserException("Email Already Exist");
        }
        Optional<User> isUsernameTaken=repo.findByUsername(user.getUsername());
        if(isUsernameTaken.isPresent())
        {
            throw new UserException("Username Already Taken");
        }
        if(user.getEmail()==null||user.getPassword()==null||user.getUsername()==null||user.getName()==null){
            throw new UserException("email,password and username are required");
        }
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        return repo.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
       Optional<User>foundUser=repo.findById(userId);
       if(foundUser.isPresent()){
           return foundUser.get();
       }
       throw new UserException("user not found with userid:"+userId);
    }
    @Override
    public String followerUser(Integer reqUserId, Integer followUserId) throws UserException {
    User followUser=findUserById(followUserId);
    User reqUser=findUserById(reqUserId);

    UserDto follower=new UserDto();
    follower.setEmail(reqUser.getEmail());
    follower.setUsername(reqUser.getUsername());
    follower.setId(reqUser.getId());
    follower.setName(reqUser.getName());
    follower.setUserImage(reqUser.getImage());

    UserDto following=new UserDto();
    following.setEmail(followUser.getEmail());
    following.setUsername(followUser.getUsername());
    following.setId(followUser.getId());
    following.setName(followUser.getName());
    following.setUserImage(followUser.getImage());

    followUser.getFollower().add(follower);
    reqUser.getFollowing().add(following);

    repo.save(followUser);
    repo.save(reqUser);
        return "you are following "+followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer unfollowUserId) throws UserException {

        User unfollowUser=findUserById(unfollowUserId);

        System.out.println("unfollow user ---- "+unfollowUser.toString());
        System.out.println("unfollow user's follower"+unfollowUser.getFollower().toString());

        User reqUser=findUserById(reqUserId);

        UserDto unfollow=new UserDto();
        unfollow.setEmail(reqUser.getEmail());
        unfollow.setUsername(reqUser.getUsername());
        unfollow.setId(reqUser.getId());
        unfollow.setName(reqUser.getName());
        unfollow.setUserImage(reqUser.getImage());


        UserDto following=new UserDto();
        following.setEmail(unfollowUser.getEmail());
        following.setUsername(unfollowUser.getUsername());
        following.setId(unfollowUser.getId());
        following.setName(unfollowUser.getName());
        following.setUserImage(unfollowUser.getImage());


        unfollowUser.getFollower().remove(unfollow);

        repo.save(reqUser);

        return "you have unfollow "+unfollowUser.getUsername();


    }

    @Override
    public List<User> findUsersByUserIds(List<Integer> userIds) {
        List<User> users=repo.findAllUserByUserIds(userIds);
        return users;
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
       Optional<User> foundUserByUsername= repo.findByUsername(username);
       if(foundUserByUsername.isPresent()){
           User user=foundUserByUsername.get();
           return user;
       }
        throw new UserException("user not exist with username"+username);
    }
    @Override
    public User findUserProfile(String token) throws UserException {
        return null;
    }




    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users=repo.findByQuery(query);
        if(users.size()==0){
            throw new UserException("user not exists");
        }
        return users;
    }
    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
        if (updatedUser.getEmail() != null)
            existingUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getMobile() != null) {
            existingUser.setMobile(updatedUser.getMobile());
        }
        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }
        if (updatedUser.getWebsite() != null) {
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if (updatedUser.getImage() != null) {
            existingUser.setImage(updatedUser.getImage());
        }
        if (updatedUser.getId().equals(existingUser.getId())) {
            System.out.println(" u " + updatedUser.getId() + " e " + existingUser.getId());
            throw new UserException("you can't update another user");

        }
        return repo.save(existingUser);
    }
   //TODO Write code here
    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        User followUser=findUserById(followUserId);
        User reqUser=findUserById(reqUserId);

        // puru follower increment -followUser
        UserDto follower=new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setUsername(reqUser.getUsername());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getImage());

        //indu following increment-reqUser
        UserDto following=new UserDto();
        following.setEmail(followUser.getEmail());
        following.setUsername(followUser.getUsername());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUserImage(followUser.getImage());


        followUser.getFollower().add(follower);
        reqUser.getFollowing().add(following);

        repo.save(followUser);
        repo.save(reqUser);
        return "you are following "+followUser.getUsername();
    }
  //TODO
    @Override
    public String unfollowUser(Integer id, Integer unfollowUserId) {
        return null;
    }
    //TODO
    /*
    private String username;
    private String name;
    private String userImage;
     */


    @Override
    public User createUserById(UserDto userDto) {
        User newuser=new User();
        newuser.setEmail(userDto.getEmail());
        newuser.setUsername(userDto.getUsername());
        repo.save(newuser);
        return newuser;
    }

    @Override
    public List<User> popularUser() {
        return null;
    }

}
