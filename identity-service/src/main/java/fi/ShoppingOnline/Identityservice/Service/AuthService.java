package fi.ShoppingOnline.Identityservice.Service;

import java.util.List;
import java.util.Optional;

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
       
        Optional<UserCredentials> user1 = userRepositary.findByUsername(userCredentials.getUsername()) ;

        if(!user1.isEmpty()){

            return "User is already present in the database" ;
        }
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
