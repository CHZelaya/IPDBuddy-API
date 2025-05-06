package oosd.sait.ipdbuddyapi.controllers;

import oosd.sait.ipdbuddyapi.dto.LoginRequestDTO;
import oosd.sait.ipdbuddyapi.dto.NewPasswordRequestDTO;
import oosd.sait.ipdbuddyapi.exceptions.NewPasswordRequiredException;
import oosd.sait.ipdbuddyapi.services.CognitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.secretKey}")
    private String clientSecret;

    @Value("${aws.cognito.redirectUri}")
    private String redirectUri;

    @Value("${aws.cognito.tokenUri}")
    private String tokenUri;


    /**
     *
     * @param request The JSON payload coming from the front end, containing the code needed to exchange for a token.
     * @param httpEntity
     * @return
     */
    @PostMapping("/exchange-code")
    public ResponseEntity<Map<String, Object>> exchangeCode(@RequestBody Map<String, String> request, HttpEntity<Object> httpEntity) {
        //? Extract the code from the request
        String code = request.get("code");

        //? The template that's being built out and will be a request to Cognito
        RestTemplate restTemplate = new RestTemplate();

        //? Setting up the headers of the request.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);


        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, entity, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token Exchange failed"));
        }

    };







    //! Not needed anymore - Cognito is handling all Login logic -> Will be deleting later.
//    /**
//     *
//     * @param loginRequest
//     * @return
//     */
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
//        try {
//            Map<String, String> tokens = cognitoService.login(loginRequest.getEmail(), loginRequest.getPassword());
//            return ResponseEntity.ok(tokens);
//        } catch (NewPasswordRequiredException e) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("error", "NEW PASSWORD REQUIRED");
//            response.put("session", e.getSession());
//            response.put("email", e.getEmail());
//            return ResponseEntity.status(HttpStatusCode.FORBIDDEN).body(response);
//        }
//    }
//
//    @PostMapping("/new-password")
//    public ResponseEntity<?> respondToNewPasswordChallenge(@RequestBody NewPasswordRequestDTO newPasswordRequest) {
//        Map<String, String> tokens = cognitoService.responseToNewPasswordChallenge(
//                newPasswordRequest.getEmail(),
//                newPasswordRequest.getNewPassword(),
//                newPasswordRequest.getSession()
//        );
//                return ResponseEntity.ok(tokens);
//    }

}//!Class
