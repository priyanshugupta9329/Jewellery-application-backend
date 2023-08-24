package com.tc.training.jewelleryapplication.service.Impl;

import com.tc.training.jewelleryapplication.config.JwtConstant;
import com.tc.training.jewelleryapplication.config.JwtProvider;
import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.repository.UserRepository;
import com.tc.training.jewelleryapplication.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent())
        {
            return user.get();
        }
        throw new UserException("User not found with id : "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email=jwtProvider.getEmailFromToken(jwt);
        User user =userRepository.findByEmail(email);

        if(user==null)
        {
            throw new UserException("User not found with email : "+email);
        }
        return user;
    }
}
