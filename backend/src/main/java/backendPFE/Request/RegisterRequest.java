package backendPFE.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;

    // Add the getter for email
    public String getEmail() {
        return email;
    }

    // Add the getter for password
    public String getPassword() {
        return password;
    }

    // Add the getter for username
    public String getUsername() {
        return username;
    }
}
