package mx.edu.beecker.security.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.service.UserService;
import mx.edu.beecker.security.controller.dto.*;
import mx.edu.beecker.security.jwt.JwtService;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody RequestLoginDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        Optional<BeanUser> optionalUser = userService.findByEmail(request.email());
        BeanUser user = optionalUser.get();
        //generate token with email (sub) and role
        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(new ResponseLoginDTO(jwt));
    }


    //controllers for recover password
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody RequestEmailDTO request) {
        userService.generateVerificationCode(request.email());
        return ResponseEntity.ok().body("Email sent correctly");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody RequestPasswordReset request) {
        userService.resetPasswordWithCode(request.email(), request.verificationCode(), request.newPassword());
        return ResponseEntity.ok().body("Password changed successfully.");
    }

}