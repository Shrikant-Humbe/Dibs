package org.me.dibs.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.me.dibs.model.Item;
import org.me.dibs.model.User;
import org.me.dibs.service.ItemService;
import org.me.dibs.service.JwtService;
import org.me.dibs.service.UserService;
import org.me.dibs.config.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestPart User user, @RequestPart(required = false) MultipartFile profilePicture) throws IOException {
        userService.addUser(user, profilePicture);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(Map.of("error", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(user.getUsername());
        ResponseCookie cookie = cookieUtil.createJwtCookie(token);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User u = userService.getUser(authentication.getName());
            return new ResponseEntity<>(u, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/claimedItems")
    public ResponseEntity<List<Item>> getClaimedItems(Principal principal) {
        List<Item> claimedItems = itemService.getClaimedItems(principal.getName());
        return new ResponseEntity<>(claimedItems, HttpStatus.OK);
    }

    @GetMapping("/postedItems")
    public ResponseEntity<List<Item>> getPostedItems(Principal principal) {
        List<Item> lostItems = itemService.getMyLostItems(principal);
        return new ResponseEntity<>(lostItems, HttpStatus.OK);
    }
}
