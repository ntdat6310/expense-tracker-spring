package dat.hcmus.expense.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dat.hcmus.expense.entity.Role;
import dat.hcmus.expense.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String accessToken = jwtUtil.resolveToken(request);
			if (accessToken == null) {
				filterChain.doFilter(request, response);
				return;
			}
			Claims claims = jwtUtil.resolveClaims(request);
			if (claims != null && jwtUtil.validateClaims(claims)) {
				UserDetails userDetails = getUserDetails(accessToken);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			throw e;
		}
		filterChain.doFilter(request, response);
	}

	private UserDetails getUserDetails(String token) {
		User userDetails = new User();
		Claims claims = jwtUtil.parseJwtClaims(token);
		String subject = (String) claims.get(Claims.SUBJECT);
		String roles = (String) claims.get("roles");

		roles = roles.replace("[", "").replace("]", "");
		String[] roleNames = roles.split(",");

		for (String aRoleName : roleNames) {
			userDetails.addRole(new Role(aRoleName));
		}

		String[] jwtSubject = subject.split(",");

		userDetails.setId(Long.parseLong(jwtSubject[0]));
		userDetails.setEmail(jwtSubject[1]);

		return userDetails;
	}

}
