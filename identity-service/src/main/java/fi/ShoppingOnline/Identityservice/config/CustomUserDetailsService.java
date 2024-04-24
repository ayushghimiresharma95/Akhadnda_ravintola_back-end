package fi.ShoppingOnline.Identityservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import fi.ShoppingOnline.Identityservice.Model.UserCredentials;
import fi.ShoppingOnline.Identityservice.Repositary.UserRepositary;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositary userRepositary ;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> userCredentials = userRepositary.findByUsername(username);
        
        return userCredentials.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found in the database"+username)) ;
    }


    
}
