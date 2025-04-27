package oosd.sait.ipdbuddyapi.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

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

            Map<String, String> tokens = new HashMap<>();
            tokens.put("idToken", authResponse.authenticationResult().idToken());
            tokens.put("accessToken", authResponse.authenticationResult().accessToken());
            return tokens;

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Login failed: " + e.awsErrorDetails().errorMessage(), e);
        }
    }






}//! Class
