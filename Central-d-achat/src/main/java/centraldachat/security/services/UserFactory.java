package centraldachat.security.services;

import centraldachat.entity.Users;


public class UserFactory {
  public static UserDetailsImpl build(Users user){

    return new UserDetailsImpl(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),user.getMobile(),user.getRole());
  }
}
