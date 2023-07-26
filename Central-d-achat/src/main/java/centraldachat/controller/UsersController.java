package centraldachat.controller;

import centraldachat.dto.UsersDTO;

import centraldachat.entity.Users;
import centraldachat.payload.response.JwtResponse;
import centraldachat.security.jwt.JwtUtils;
import centraldachat.security.services.UserDetailsImpl;
import centraldachat.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;



@RestController
public class UsersController {

    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsersService usersService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    public UsersController(UsersService usersService) {
        super();
        this.usersService = usersService;
    }

    @Autowired
    UsersService userService;

    @GetMapping("/oauth/Users")
    public List<Users> retrieveAllUsers() {

        System.out.println(userService.test());
        return userService.retrieveAllUsers();
    }


    @PostMapping("/oauth/Users")
    public Users addUser(@RequestBody Users u) {
        return userService.addUser(u);
    }

    @PutMapping("/oauth/Users")
    public Users updateUser(Users e) {
        return userService.updateUser(e);
    }


    @DeleteMapping("/oauth/Users/{idUser}")
    public String removeUser(@PathVariable("idUser") Integer idUser) {
        userService.removeUser(idUser);
        return "Deleted Successfully";
    }


    @PostMapping("/oauth/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody UsersDTO usersDTO) {
        Users user = modelMapper.map(usersDTO, Users.class);
        System.out.println(user.getEmail());
        if (!usersService.existsEmail(user.getEmail())) {
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity<>(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail()
        ), HttpStatus.OK);
    }


    @PostMapping("/oauth/register")
    public ResponseEntity<Object> processRegister(@RequestBody UsersDTO usersDTO) throws UnsupportedEncodingException, MessagingException {
        Users userReq = modelMapper.map(usersDTO, Users.class);
        ResponseEntity<Users> user = usersService.register(userReq);

        if (user.getStatusCodeValue() == 200) {
            UsersDTO userRes = modelMapper.map(user.getBody(), UsersDTO.class);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } else if (user.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.OK);
        }
    }

//    @GetMapping("/aouth/test")
//
//    public ResponseEntity<Object> test() {
//
//
//        return new ResponseEntity<>(FOUND, HttpStatus.OK);
//    }

    @GetMapping("oauth/verify/{code}")
    public ResponseEntity<Object> verifyUser(@PathVariable String code) {
        ResponseEntity<Users> user = usersService.verify(code);
        if (user.getStatusCodeValue() == 200) {
            UsersDTO usersDTO = modelMapper.map(user.getBody(), UsersDTO.class);
            return new ResponseEntity<>(usersDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
        }

    }

    @PostMapping("/oauth/forgetPassword")

    public ResponseEntity<Object> forgetPassword(@RequestBody UsersDTO usersDTO) throws UnsupportedEncodingException, MessagingException {

        Users userReq = modelMapper.map(usersDTO, Users.class);
        userService.sendPassMail(userReq.getEmail());

        return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }

    @PostMapping("/oauth/resetPassword/{token}")

    public ResponseEntity<Object> resetPass(@PathVariable String token, @RequestBody UsersDTO usersDTO) {
        Users userReq = modelMapper.map(usersDTO, Users.class);
        System.out.println(token+ "  "+ userReq.getPassword());
        usersService.verifyPassToken(token,userReq.getPassword());
        return new ResponseEntity<>(FOUND, HttpStatus.OK);





    }

}


