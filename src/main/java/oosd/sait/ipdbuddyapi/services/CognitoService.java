package oosd.sait.ipdbuddyapi.services;

import jakarta.annotation.PostConstruct;
import oosd.sait.ipdbuddyapi.exceptions.NewPasswordRequiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class CognitoService {

    private CognitoIdentityProviderClient cognitoClient;
    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientID;

    @Value("${aws.cognito.accessKey}")
    private String awsAccessKeyId;

    @Value("${aws.cognito.secretKey}")
    private String awsSecretAccessKey;

    @Value("${aws.cognito.region}")
    private String region;

    @PostConstruct
    private CognitoIdentityProviderClient createCognitoClient() {
        return CognitoIdentityProviderClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey)
                        )
                )
                .build();
    }


    /**
     * Logs the user in, using AWS Cognito service.
     * This method checks for an authentication response from Cognito.
     * If there is no challenge, the login will be successful
     * If Cognito returns a NEW_PASSWORD_REQUIRED challenge, the method throws a {@link NewPasswordRequiredException}
     * If another type of challenge is found, a {@link RuntimeException} is thrown and logs the challenge type.
     *
     * @param email The username provided by the user
     * @param password The user's password
     * @return If successful, Sends a response containing the valid tokens to the client.
     */
    public Map<String, String> login(String email, String password) {
        try (CognitoIdentityProviderClient cognitoClient = createCognitoClient()) {
            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", email);
            authParams.put("PASSWORD", password);

            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .userPoolId(userPoolId)
                    .clientId(clientID)
                    .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .authParameters(authParams)
                    .build();

            AdminInitiateAuthResponse authResponse = cognitoClient.adminInitiateAuth(authRequest);

            if (authResponse.authenticationResult() != null) {
                Map<String, String> tokens = new HashMap<>();
                tokens.put("idToken", authResponse.authenticationResult().idToken());
                tokens.put("accessToken", authResponse.authenticationResult().accessToken());
                return tokens;
            }else if (authResponse.challengeName() != null &&
            authResponse.challengeName().toString().equals("NEW_PASSWORD_REQUIRED")) {
                throw new NewPasswordRequiredException("New password required", authResponse.session(), email);
            } else {
                throw new RuntimeException("Authentication challenge required: " + authResponse.challengeNameAsString());
            }

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Login failed: " + e.awsErrorDetails().errorMessage(), e);
        }
    }


    /**
     *
     * @param email
     * @param newPassword
     * @param session
     * @return
     */
    public Map<String, String> responseToNewPasswordChallenge(String email, String newPassword, String session){
        try (CognitoIdentityProviderClient cognitoClient = createCognitoClient()) {
            Map<String, String> challengeResponses = new HashMap<>();
            challengeResponses.put("USERNAME", email);
            challengeResponses.put("NEW_PASSWORD", newPassword);

            // Mapping the request
            RespondToAuthChallengeRequest challengeRequest = RespondToAuthChallengeRequest.builder()
                    .challengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                    .clientId(clientID)
                    .session(session)
                    .challengeResponses(challengeResponses)
                    .build();

            // Creating the response
            RespondToAuthChallengeResponse response = cognitoClient.respondToAuthChallenge(challengeRequest);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("idToken", response.authenticationResult().idToken());
            tokens.put("accessToken", response.authenticationResult().accessToken());
            return tokens;
        }    catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to respond to new password challenge: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

}//! Class
