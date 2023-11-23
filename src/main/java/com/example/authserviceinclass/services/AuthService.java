package com.example.authserviceinclass.services;

//import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.authserviceinclass.dtos.UserDto;
import com.example.authserviceinclass.exceptions.InvalidPassword_ex;
import com.example.authserviceinclass.exceptions.UserAlreadyExisting;
import com.example.authserviceinclass.exceptions.UserDoesNotExist_ex;
import com.example.authserviceinclass.models.Session;
import com.example.authserviceinclass.models.SessionStatus;
import com.example.authserviceinclass.models.User;
import com.example.authserviceinclass.repositories.SessionRepository;
import com.example.authserviceinclass.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository repository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder encoder;

    public AuthService(UserRepository repository,
                       SessionRepository sessionRepository){
        this.repository = repository;
        this.sessionRepository = sessionRepository;
        this.encoder = new BCryptPasswordEncoder();
    }
    public UserDto signUp(String email, String password) throws  UserAlreadyExisting{
        Optional<User> existingUser = repository.findByEmail(email);
        if(!(existingUser.isEmpty())){
            throw new UserAlreadyExisting("User with " + email + "already exists trying logging in");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));  //encoded password should be saved
        User savedUser = repository.save(user);
        return UserDto.userToDto(savedUser);

    }
    public ResponseEntity<UserDto> login(String email, String password)
            throws UserDoesNotExist_ex, InvalidPassword_ex{
        //find user with that email
        Optional<User> userOpt = repository.findByEmail(email);
        if(userOpt.isEmpty()){
            throw new UserDoesNotExist_ex("User with email" + email + " does not exis please sign Up");
        }else{
            if(encoder.matches(password,userOpt.get().getPassword())){
              //create Session and save a token
              //  String token = RandomStringUtils.randomAscii(20);
                UserDto dto = UserDto.userToDto(userOpt.get());
                MultiValueMapAdapter<String,String> headers =  new MultiValueMapAdapter<>(new HashMap<>());
//                headers.add("AUTH_TOKEN",token);

                //JWT token Logic Begin
               // String SECRET = "53675Shah";
                Map<String, Object> claims = new HashMap<>();
                claims.put("user",userOpt.get().getId());
                claims.put("email",userOpt.get().getEmail());
                claims.put("Roles",userOpt.get().getRoles());
                String token = Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userOpt.get().getEmail())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

                //JWT token Logic End ---> Reference from GFG
                headers.add("AUTH_TOKEN",token);
                Session session = new Session();
                session.setSessionStatus(SessionStatus.ACTIVE);
                session.setToken(token);
                session.setUser(userOpt.get());
                sessionRepository.save(session);
//                dto.setToken(token);
                return new ResponseEntity<>(dto,
                        headers,
                        HttpStatus.OK);
            }else{
                //throw invalid password exception
                throw new InvalidPassword_ex("Incorrect Password try again");
            }
        }
    }
    public SessionStatus validateRequest(String token,Long userId) {
        Optional<Session> session = sessionRepository
                .findByTokenAndUser_Id(token, userId);
        if ((session.isEmpty())) {

            return SessionStatus.EXPIRED;
        }
        else{
            return session.get().getSessionStatus();

        }

    }
    public ResponseEntity<Void> logout(String token,Long userId){
        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(token,userId);
        if(session.isEmpty()){
            return  null;
        }
        session.get().setSessionStatus(SessionStatus.EXPIRED);
        sessionRepository.save(session.get());
        return ResponseEntity.ok().build();
    }
    private Key getSignKey() {
        String SECRET = "Shahid5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
