package oosd.sait.ipdbuddyapi.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Component
public class JwtTokenValidator {

    public boolean validateToken(String token) {
        return token != null && !token.isEmpty();
    }

    public Authentication getAuthentication(String token) {
        String username = extractUsernameFromToken(token);

        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }

    private String extractUsernameFromToken(String token) {

        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
        String payloadJson = new String(Base64.getDecoder().decode(parts[1]));


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode payload = objectMapper.readTree(payloadJson);
            return payload.get("email").asText();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse JWT token payload");
        }

    }
} //! Class
