package com.devspotlight.devspotlight.config;

import com.devspotlight.devspotlight.dto.UserDTO;
import com.devspotlight.devspotlight.model.Role;
import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class Auth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
//        se for authenticado, pega os dados vindo do github
        if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String name = attributes.get("name") != null ? attributes.get("name").toString() : "";
            String email = attributes.get("email") != null ? attributes.get("email").toString() : "";
            String username = attributes.get("login") != null ? attributes.get("login").toString() : "";
            String gitHubPorfileLink = attributes.get("url") != null ? attributes.get("url").toString() : "";
            String githubProfilePhoto = attributes.get("avatar_url") != null ? attributes.get("avatar_url").toString() : "";
            System.out.println("USERNAME ==================================");
            System.out.println(principal.getAttributes());
            userService.findByUsername(username)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        UserDTO user = new UserDTO();
                        user.setEmail(email);
                        user.setUsername(username);
                        user.setName(name);
                        user.setGithubProfileLink(gitHubPorfileLink);
                        user.setGithubProfilePhoto(githubProfilePhoto);
                        user.setRole(Role.USER);
                        userService.createUser(user);

                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);

                    });
        }


        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
