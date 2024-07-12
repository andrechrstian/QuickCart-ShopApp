package org.example.tokonyadia.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.tokonyadia.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
   private final JwtUtil jwtUtil;
   private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           //Get and validate request client
           String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

           String clientToken = null;
           if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
               clientToken = headerAuth.substring(7);
           }

           if (clientToken != null && jwtUtil.verifyJwtToken(clientToken)) {
               Map<String,String> userInfo = jwtUtil.getUserInfoByToken(clientToken);
               UserDetails user = userService.loadUserById(userInfo.get("userId"));
               System.out.println("username : " + user.getUsername());
               System.out.println("password : " + user.getPassword());

               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource());

               SecurityContextHolder.getContext().setAuthentication(authenticationToken);

           }
       } catch (JWTVerificationException e) {
           throw  new AuthenticationException("Auth Errorr : " + e.getMessage());
       }

        filterChain.doFilter(request,response);
    }
}
