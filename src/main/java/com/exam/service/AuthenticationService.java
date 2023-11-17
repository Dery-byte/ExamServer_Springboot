package com.exam.service;

import com.exam.auth.AuthenticationRequest;
import com.exam.auth.AuthenticationResponse;
import com.exam.auth.RegisterRequest;
import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.repository.TokenRepository;
import com.exam.repository.UserRepository;
import com.exam.token.Token;
import com.exam.token.TokenType;
import com.exam.model.Role;
import com.exam.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UserFoundException {
        var userExist = userRepository.findByUsername(request.getUsername());
        if (userExist.isPresent()) {
            System.out.println("User is already in the system");
            throw new UserFoundException();
        }
        else
        {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.NORMAL)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            var jwtToken = jwtService.generateToken((UserDetails) user);
            revokeAllUserTokens((User) user);
            saveUserToken((User) user, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//        var tokens = tokenRepository.findByToken(user.getTokens().toString());


        //deleting the expired tokens

        if (validUserTokens.isEmpty())
//            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public Optional<User> getUser(String email ){
        return this.userRepository.findByUsername(email);
    }

//    public Optional<User> getUserByUserName(String username){
//        return this.userRepository.findByUsername(username);
//    }

    public List<User> getUserByUserName(User user){
        return Collections.singletonList(this.userRepository.save(user));
    }


    public List<User> getAllUsers() {
        return  userRepository.findAll();
    }

    public User getUserById(Integer user_id){
        return (User) userRepository.findById(user_id).get();
    }
}
