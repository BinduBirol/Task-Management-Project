package com.bnroll.tm.auth.config;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginRateLimitFilter extends OncePerRequestFilter {

	@Autowired
	private StringRedisTemplate redis;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		if ("/login".equals(path)) {

			String ip = request.getRemoteAddr();
			String key = "login:ip:" + ip;

			Long count = redis.opsForValue().increment(key);

			if (count == 1) {
				redis.expire(key, Duration.ofMinutes(1));
			}

			if (count > 2) {
				response.setStatus(429);
				response.getWriter().write("Too many login attempts");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}