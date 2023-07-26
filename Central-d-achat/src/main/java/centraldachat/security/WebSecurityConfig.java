package centraldachat.security;

import centraldachat.security.jwt.AuthEntryPointJwt;
import centraldachat.security.jwt.AuthTokenFilter;
import centraldachat.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  //    @Autowired
  //    private DataSource dataSource;
  //
  //    @Bean
  //    public UserDetailsService userDetailsService() {
  //        return new CustomUserDetailsService();
  //    }
  //
  //    @Bean
  //    public BCryptPasswordEncoder passwordEncoder() {
  //        return new BCryptPasswordEncoder();
  //    }
  //
  //    @Bean
  //    public DaoAuthenticationProvider authenticationProvider() {
  //        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
  //        authProvider.setUserDetailsService(userDetailsService());
  //        authProvider.setPasswordEncoder(passwordEncoder());
  //
  //        return authProvider;
  //    }
  //
  //    @Override
  //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  //        auth.authenticationProvider(authenticationProvider());
  //    }
  //
  //    @Override
  //    protected void configure(HttpSecurity http) throws Exception{
  //        http.cors().and().csrf().disable();
  //    }

  //    @Override
  //    protected void configure(HttpSecurity http) throws Exception {
  //         http.cors().and().csrf().disable()
  //                .authorizeRequests()
  //                .antMatchers("/users").authenticated()
  //                .anyRequest().permitAll()
  //                .and()
  //                .formLogin()
  //                .loginPage("/login")
  //                .usernameParameter("email")
  //                .defaultSuccessUrl("/home")
  //                .permitAll()
  //                .and()
  //                .logout()
  //                .logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
  //    }

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/oauth/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

}
