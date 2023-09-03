package com.tc.training.jewelleryapplication.controller;


import com.tc.training.jewelleryapplication.config.JwtProvider;
import com.tc.training.jewelleryapplication.email.EmailService;
import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.Cart;
import com.tc.training.jewelleryapplication.model.User;
import com.tc.training.jewelleryapplication.repository.UserRepository;
import com.tc.training.jewelleryapplication.request.LoginRequest;
import com.tc.training.jewelleryapplication.response.AuthResponse;
import com.tc.training.jewelleryapplication.service.CartService;
import com.tc.training.jewelleryapplication.service.Impl.CustomUserServiceImplementation;
import freemarker.template.TemplateException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;

    private JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder;

    private CustomUserServiceImplementation customUserService;

    private CartService cartService;

    @Autowired
    private EmailService emailService;

    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, CustomUserServiceImplementation customUserService, CartService cartService) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.customUserService = customUserService;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws Exception {

        String email=user.getEmail();
        String password=user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();

        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email Is Already Used With Another Account");
        }

        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser=userRepository.save(createdUser);
        Cart cart=cartService.createCart(savedUser);

//        // Send welcome email
//        String emailSubject = "Welcome to Our Jewellery Website";
//        String emailText = "Thank you for signing up!";
//
//        emailService.sendEmail(email, emailSubject, emailText);


        // Send welcome email
//        String emailSubject = "Welcome to Our Jewellery Website";
//        String emailTemplate = "welcome-email"; // This should match the name of your FreeMarker template (without the extension)
//
//        Map<String, Object> emailModel = new HashMap<>();
//        emailModel.put("username", user.getEmail()); // Sending the email as username for demonstration purposes
//        emailModel.put("password", password); // Sending the password
//
//        emailService.sendEmailWithTemplate(email, emailSubject, emailTemplate, emailModel);


        emailService.sendHtmlEmail(user.getEmail(), user.getEmail(), password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@Valid @RequestBody LoginRequest loginRequest){

        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication=authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signin Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username....");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
