package com.bnroll.tm.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bnroll.tm.auth.util.JwtUtil;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		// 1. No token → continue request
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 2. Extract token
		String token = header.substring(7);

		String username;

		try {
			username = jwtUtil.extractUsername(token);
		} catch (Exception e) {
			filterChain.doFilter(request, response);
			return;
		}

		// 3. If already authenticated → skip
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// 4. Validate token
			if (jwtUtil.validateToken(token)) {

				// 5. Load user
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// 6. Create authentication object
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());

				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// 7. SET AUTHENTICATION (MOST IMPORTANT STEP)
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		// 8. Continue request
		filterChain.doFilter(request, response);
	}
}