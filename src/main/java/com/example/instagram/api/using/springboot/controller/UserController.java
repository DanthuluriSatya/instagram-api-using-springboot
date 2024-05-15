package com.example.instagram.api.using.springboot.controller;

import com.example.instagram.api.using.springboot.dto.UserDto;
import com.example.instagram.api.using.springboot.exceptions.UserException;
import com.example.instagram.api.using.springboot.model.User;
import com.example.instagram.api.using.springboot.response.MessageResponse;
import com.example.instagram.api.using.springboot.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> CreateUserByIdHandler(@RequestBody UserDto userDto) throws UserException{

        User user=userService.createUserById(userDto);

        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException {
        User user=userService.findUserById(id);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUsernameHandler(@PathVariable("username") String username) throws UserException{
        User user=userService.findUserByUsername(username);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @PutMapping("/follow/{followUserId}/{reqUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId, @PathVariable Integer reqUserId)  throws UserException{
        //User reqUser=userService.findUserProfile(token);


        String message=userService.followUser(reqUserId, followUserId);
        MessageResponse res=new MessageResponse(message);
        return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
    }

    @PutMapping("/unfollow/{unfollowUserId}")
    public ResponseEntity<MessageResponse> unfollowUserHandler(@RequestHeader("Authorization") String token, @PathVariable Integer unfollowUserId) throws UserException{

        User reqUser=userService.findUserProfile(token);

        String message=userService.unfollowUser(reqUser.getId(), unfollowUserId);
        MessageResponse res=new MessageResponse(message);
        return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
    }


    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findAllUsersByUserIdsHandler(@PathVariable List<Integer> userIds){
        List<User> users=userService.findUsersByUserIds(userIds);
       System.out.println("userIds------"+userIds);
       return new ResponseEntity<List<User>>(users,HttpStatus.ACCEPTED);
   }
   @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q")String query) throws UserException{
        List<User> users=userService.searchUser(query);
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
   }
   @GetMapping("/populer")
    public ResponseEntity<List<User>> populerUsersHandler(){
        List<User> populerUsers=userService.popularUser();
        return new ResponseEntity<List<User>>(populerUsers,HttpStatus.OK);
   }

}
