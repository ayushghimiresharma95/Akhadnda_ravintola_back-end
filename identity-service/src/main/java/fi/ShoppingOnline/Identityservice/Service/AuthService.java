package fi.ShoppingOnline.Identityservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fi.ShoppingOnline.Identityservice.Model.UserCredentials;
import fi.ShoppingOnline.Identityservice.Repositary.UserRepositary;



@Service
public class AuthService {
    
    @Autowired
    private UserRepositary userRepositary;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private JwtService jwtService ;

    public String saveUser(UserCredentials userCredentials){
        userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        userRepositary.save(userCredentials);
        return "user created" ;
    }
    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
