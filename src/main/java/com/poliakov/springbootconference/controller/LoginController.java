package com.poliakov.springbootconference.controller;

import com.poliakov.springbootconference.converters.Converter;
import com.poliakov.springbootconference.dto.UserDto;
import com.poliakov.springbootconference.model.User;
import com.poliakov.springbootconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Converter converter;

    @Autowired
    public LoginController(UserService userService, BCryptPasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, Converter converter) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.converter = converter;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        if(request.getUserPrincipal() != null) {
            return "redirect:/conferences";
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    private void manuallyLogin(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping("/register")
    public ModelAndView register(UserDto userDto, HttpServletRequest request) {
        if (userService.userExists(userDto.getUsername())) {
            ModelAndView mav = new ModelAndView("register", "user", userDto);
            mav.addObject("error", "User already exists");
            return mav;
        } else {
            User user = converter.toUserModel(userDto, passwordEncoder);
            userService.saveUser(user);
            manuallyLogin(userDto.getUsername(), userDto.getPassword(), request);
            return new ModelAndView( "redirect:/conferences");
        }
    }

    @GetMapping("/logout")
    @RolesAllowed({"MODERATOR", "SPEAKER", "PARTICIPANT"})
    public String logoutForm() {
        return "logout";
    }

    @PostMapping("/logout")
    @RolesAllowed({"MODERATOR", "SPEAKER", "PARTICIPANT"})
    public String logout() {
        return "logout";
    }

}
