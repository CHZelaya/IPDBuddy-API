package oosd.sait.ipdbuddyapi.controllers;

import oosd.sait.ipdbuddyapi.dto.LoginRequestDTO;
import oosd.sait.ipdbuddyapi.dto.NewPasswordRequestDTO;
import oosd.sait.ipdbuddyapi.exceptions.NewPasswordRequiredException;
import oosd.sait.ipdbuddyapi.services.CognitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private CognitoService cognitoService;

    /**
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Map<String, String> tokens = cognitoService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(tokens);
        } catch (NewPasswordRequiredException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "NEW PASSWORD REQUIRED");
            response.put("session", e.getSession());
            response.put("email", e.getEmail());
            return ResponseEntity.status(HttpStatusCode.FORBIDDEN).body(response);
        }
    }

    @PostMapping("/new-password")
    public ResponseEntity<?> respondToNewPasswordChallenge(@RequestBody NewPasswordRequestDTO newPasswordRequest) {
        Map<String, String> tokens = cognitoService.responseToNewPasswordChallenge(
                newPasswordRequest.getEmail(),
                newPasswordRequest.getNewPassword(),
                newPasswordRequest.getSession()
        );
                return ResponseEntity.ok(tokens);
    }

}//!Class
