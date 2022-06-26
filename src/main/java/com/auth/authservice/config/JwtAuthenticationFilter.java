package com.auth.authservice.config;

import com.auth.authservice.helper.JwtUtil;
import com.auth.authservice.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtutil;

    @Autowired
    private CustomUserService customUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get header
        //Start with Bearer
        //validate
        String reqTokenHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken = null;

        if (reqTokenHeader != null && reqTokenHeader.startsWith("Bearer ")) {
            String tokenWithoutBearer = reqTokenHeader.substring(7);

            try{
                userName = this.jwtutil.extractUsername(tokenWithoutBearer);
            }catch (Exception e){
                e.printStackTrace();
                //throw new Exception("Invalid token!!");
            }

            UserDetails user = this.customUserService.loadUserByUsername(userName);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else {
                //any message
                System.out.println("Token not validated!!");
            }
        }

        filterChain.doFilter(request,response);

    }
}
