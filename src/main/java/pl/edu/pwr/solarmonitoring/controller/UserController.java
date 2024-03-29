/*
 * SolarMonitoring
 * Copyright (C) 2022 Mikołaj Chmielecki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * LICENSE file in root directory contains a copy of the GNU General Public License.
 */

package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.solarmonitoring.config.JwtTokenUtil;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.JwtRequest;
import pl.edu.pwr.solarmonitoring.model.request.UserEditRequest;
import pl.edu.pwr.solarmonitoring.model.request.UserRequest;
import pl.edu.pwr.solarmonitoring.model.response.JwtResponse;
import pl.edu.pwr.solarmonitoring.model.response.StringResponse;
import pl.edu.pwr.solarmonitoring.model.response.UserResponse;
import pl.edu.pwr.solarmonitoring.service.UserService;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService jwtInMemoryUserDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity getUser(Authentication authentication) {
        try {
            User user = UserUtils.fromAuthentication(authentication);
            UserResponse response = userService.getUser(user);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserRequest userRequest) {
        try {
            userService.createUser(userRequest);
            return new ResponseEntity(new StringResponse(String.format("User %s created", userRequest.getUsername())), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity updateUser(Authentication authentication, @RequestBody UserEditRequest userEditRequest) {
        try {
            User user = UserUtils.fromAuthentication(authentication);
            userService.updateUser(user, userEditRequest);
            return new ResponseEntity(new StringResponse(String.format("User %s updated", user.getUsername())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        try {
            User user = UserUtils.fromAuthentication(authentication);
            userService.deleteUser(user);
            return new ResponseEntity(new StringResponse(String.format("User %s deleted", user.getUsername())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
