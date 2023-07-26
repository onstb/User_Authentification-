package centraldachat.security.services;


import centraldachat.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String FirstName;
  private String LastName;
  private String email;
  private String password;
  private String Mobile;
  private Role role;


  public UserDetailsImpl(Long id,String FirstName, String LastName, String email, String password,String Mobile,Role role) {
    this.id = id;
    this.FirstName=FirstName;
    this.LastName=LastName;
    this.email = email;
    this.password = password;
    this.Mobile=Mobile;
    this.role=role;

  }



  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
  public String getFirstName() {
    return FirstName;
  }
  public String getLastName() {
    return LastName;
  }
  public String getMobile() {
    return Mobile;
  }
  public Role getRole() {
    return this.role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return null;
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
