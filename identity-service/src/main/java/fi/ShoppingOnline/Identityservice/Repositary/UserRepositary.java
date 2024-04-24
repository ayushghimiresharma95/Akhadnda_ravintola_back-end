package fi.ShoppingOnline.Identityservice.Repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ShoppingOnline.Identityservice.Model.UserCredentials;
import java.util.List;


public interface UserRepositary extends JpaRepository<UserCredentials,Long> {

   Optional<UserCredentials> findByUsername(String username);

}
