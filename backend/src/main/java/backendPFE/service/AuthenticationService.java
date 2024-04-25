package backendPFE.service;

import backendPFE.Request.AuthenticationRequest;
import backendPFE.Request.RegisterRequest;
import backendPFE.Request.UpdateUserRequest;
import backendPFE.Exceptions.UserNotFoundException;
import backendPFE.response.AuthenticationResponse;
import backendPFE.security.config.JwtService;
import backendPFE.models.Role;
import backendPFE.models.User;
import backendPFE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // You can use a separate method to create a new user for better organization
        User user = createUserFromRequest(request);
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().name()) // Assuming Role is an Enum
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> optionalUser = repository.findByEmail(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Generate JWT token with the single role included
            String jwtToken = jwtService.generateToken(user.getRole().name(), userDetails);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .id(user.getId())
                    .email(userDetails.getUsername())
                    .username(userDetails.getUsername())
                    .role(user.getRole().name())
                    .build();
        } else {
            // Handle the case where the user is not found
            throw new UserNotFoundException("User not found");
        }
    }

    // Separate method to create a new user
    private User createUserFromRequest(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.Technicien)
                .build();
    }

    // Assuming you have a method to get the user's role from UserDetails
    private String getUserRole(UserDetails userDetails) {
        Optional<User> optionalUser = repository.findByEmail(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getRole().name(); // Assuming Role is an Enum
        }

        return "USER";
    }
    public void updateUser(Integer userId, UpdateUserRequest updateUserRequest) {
        // Retrieve the user from the database
        User existingUser = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Update user information based on the request
        existingUser.setUsername(updateUserRequest.getUsername());
        existingUser.setEmail(updateUserRequest.getEmail());
        // Update other fields as needed

        // Save the updated user to the database
        repository.save(existingUser);
    }

}
