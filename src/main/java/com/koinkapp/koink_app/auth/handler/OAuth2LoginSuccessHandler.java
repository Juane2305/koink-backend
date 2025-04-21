package com.koinkapp.koink_app.auth.handler;

import com.koinkapp.koink_app.auth.model.AuthProvider;
import com.koinkapp.koink_app.auth.security.JwtTokenProvider;
import com.koinkapp.koink_app.user.model.User;
import com.koinkapp.koink_app.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Optional<User> existingUser = userRepository.findByEmail(email);
        boolean isNew = existingUser.isEmpty(); // ✅ MOVIDO ARRIBA

        User user = existingUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProvider(AuthProvider.GOOGLE);
            newUser.setAlertsByEmail(true);
            newUser.setCurrency("ARS");
            newUser.setEmailVerified(true);
            return userRepository.save(newUser);
        });

        String jwt = jwtTokenProvider.generateToken(user);


        response.sendRedirect("http://localhost:5173/oauth2/redirect?token=" + jwt + "&new=" + isNew);
    }
}
