package com.example.instagram.api.using.springboot.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.instagram.api.using.springboot.dto.UserDto;
import com.example.instagram.api.using.springboot.model.Post;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //it is one-one relation between user and comment.
    @Embedded
    @NotNull
    @AttributeOverride(name="id",column = @Column(name="user_id"))
    private UserDto userDto;

    @NotNull
    private String content;

    @Embedded
    @ElementCollection
    private Set<UserDto> likedByUsers= new HashSet<>();

    ///comment will be there for post..we will not get all the post fields we will get only post_id
    @ManyToOne
    @JoinColumn(name = "post_id") //from post table
    private Post post;
    // postdao.save(post)--success
    //commnetd

    private LocalDateTime createdAt;


    public Comment() {
        // TODO Auto-generated constructor stub
    }


    public Comment(Integer id, @NotNull UserDto userDto, @NotNull String content, Set<UserDto> likedByUsers, Post post,
                    LocalDateTime createdAt) {
        super();
        this.id = id;
        this.userDto = userDto;
        this.content = content;
        this.likedByUsers = likedByUsers;
        this.post = post;
        this.createdAt = createdAt;
    }

    public Set<UserDto> getLikedByUsers() {
        return likedByUsers;
    }


    public void setLikedByUsers(Set<UserDto> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDto getUserDto() {
        return userDto;
    }


    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }


    @Override
    public String toString() {
        return "Comments [id=" + id + ", userDto=" + userDto + ", content=" + content + ", likedByUsers=" + likedByUsers
                + "]";
    }


}
