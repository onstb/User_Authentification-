package centraldachat.repository;

import centraldachat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

  @Query("SELECT u FROM Users u WHERE u.email = ?1")
  public Optional<Users> findByEmail(String email);

  @Query("SELECT u FROM Users u WHERE u.PasswordToken = ?1")
  public Optional<Users> findByPasswordToken(String token);
  @Query("SELECT u FROM Users u WHERE u.verificationCode = ?1")
  public Optional<Users> findByVerificationCode(String code);


  boolean existsByEmail(String email);

}
