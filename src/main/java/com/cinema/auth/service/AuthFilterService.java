package com.cinema.auth.service;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;


@Service
public class AuthFilterService extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthFilterService.class);

    public AuthFilterService(JwtService jwtService, UserDetailsService userDetailsService) {

		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, 

									@NotNull HttpServletResponse response, 
									@NotNull FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		String jwt;
		String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or does not start with Bearer.");

			filterChain.doFilter(request, response);
			return;
		}

            // Extract JWT
            logger.info("Extracting JWT from Authorization header.");

		jwt = authHeader.substring(7);

		// extract username
		username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("Validating token for user: {}", username);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

        filterChain.doFilter(request, response);
        logger.info("Filter chain processed successfully.");

	}

}
