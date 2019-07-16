//package com.uball.uballapp.services;

import com.uball.uballapp.models.User;
import com.uball.uballapp.models.UserWithRoles;
import com.uball.uballapp.repos.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

//@Service("usersSvc")
//public class UserService {
//
//    UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public boolean isLoggedIn() {
//        boolean isAnonymousUser =
//                SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
//        return ! isAnonymousUser;
//    }
//
//    // Returns a user obj directly from the DB
//    public User loggedInUser() {
//
//        if (! isLoggedIn()) {
//            return null;
//        }
//
//        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return userRepository.findOne(sessionUser.getId());
//    }
//
//    // Checks if the user is the owner of the post
//    public boolean isOwner(User postUser){
//        if(isLoggedIn()){
//            return (postUser.getEmail().equals(loggedInUser().getEmail()));
//        }
//
//        return false;
//
//    }
//
//
//    // Edit controls show up if the user is logged in and it's the same user viewing the file
//    public boolean canEditProfile(User profileUser){
//        return isLoggedIn() && (profileUser.getId() == loggedInUser().getId());
//    }
//
//    public void authenticate(User user) {
//        UserDetails userDetails = new UserWithRoles(user, Collections.emptyList());
//        Authentication auth = new UsernamePasswordAuthenticationToken(
//                userDetails,
//                userDetails.getPassword(),
//                userDetails.getAuthorities()
//        );
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(auth);
//    }


//}
