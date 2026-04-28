package com.bnroll.tm.auth.config.redis;

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
public class RateLimitFilter extends OncePerRequestFilter {

	@Autowired
	private StringRedisTemplate redis;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();
		String method = request.getMethod();

		if ("POST".equalsIgnoreCase(method) && path.contains("/login")) {

			String ip = request.getRemoteAddr();
			String key = "rl:login:ip:" + ip;

			Long count = redis.opsForValue().increment(key);

			if (count == 1) {
				redis.expire(key, Duration.ofMinutes(1));
			}

			if (count > 3) {

				response.setStatus(429);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				String json = """
						{
						  "message": "Too many login attempts. Try again later.",
						  "errorCode": "RATE_LIMIT_EXCEEDED",
						  "retryAfterSeconds": 60
						}
						""";

				response.getWriter().write(json);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

}