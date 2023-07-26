package centraldachat.dto;


//import centraldachat.entity.Roles;
import centraldachat.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersDTO implements Serializable {

  private Long id;
  private String email;
  private String password;
  private String FirstName;
  private String LastName;
  private String Mobile;
  private String verificationCode;
  private String PasswordToken;
  private Role role;
//  private Set<Roles> roles;
  private boolean enabled;
}
