package org.example.szoftverleltar;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<User, Integer>
{
    Optional<User> findByEmail(String email); // email alapján lesz a bejelentkezés
}
