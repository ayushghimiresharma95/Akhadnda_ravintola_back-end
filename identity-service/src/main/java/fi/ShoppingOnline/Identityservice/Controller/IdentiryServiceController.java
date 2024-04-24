package fi.ShoppingOnline.Identityservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.ShoppingOnline.Identityservice.Model.AuthRequest;
import fi.ShoppingOnline.Identityservice.Model.UserCredentials;
import fi.ShoppingOnline.Identityservice.Service.AuthService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
public class IdentiryServiceController {
    
    @Autowired
    private  AuthService authservice ;

    @Autowired
    private  AuthenticationManager authenticationManager ;


    
    
    @PostMapping("/register")
    private String addNewUser(@RequestBody UserCredentials userCredentials){
        return authservice.saveUser(userCredentials);
    }

    @PostMapping("/token")
    private String getToken(@RequestBody AuthRequest authRequest){
        Authentication ayAuthentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())) ;
        if(ayAuthentication.isAuthenticated()){
            return authservice.generateToken(authRequest.getUsername()) ;
        } else {
            throw new RuntimeException("Invalid user access") ;
        }
        
    }
    @GetMapping("/validate")
    public String getValidate(@RequestParam("token") String token){
        authservice.validateToken(token);       
        return "Token is valid" ;
    }
}
