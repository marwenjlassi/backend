package backendPFE.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Integer id; // Include user ID
    private String username;
    private String email;
    private String role;

    // Add the builder() method
    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }
}
