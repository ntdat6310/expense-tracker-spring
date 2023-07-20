package dat.hcmus.expense.security;

import java.util.Date;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import dat.hcmus.expense.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {
//	@Value("${app.jwt.secret}")
	private String SECRET_KEY = "123123";
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

	public String createToken(User user) {
		return Jwts.builder().setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
				.setIssuer("LearnJavaSpring").claim("roles", user.getRoles().toString()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

	public Claims parseJwtClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public Claims resolveClaims(HttpServletRequest req) {
		try {
			String token = resolveToken(req);
			if (token != null) {
				return parseJwtClaims(token);
			}
		} catch (ExpiredJwtException ex) {
			req.setAttribute("JWT expired", ex.getMessage());
			throw ex;
		} catch (IllegalArgumentException ex) {
			req.setAttribute("Token is null, empty or only whitespace", ex.getMessage());
			throw ex;
		} catch (MalformedJwtException ex) {
			req.setAttribute("JWT is invalid", ex);
			throw ex;
		} catch (UnsupportedJwtException ex) {
			req.setAttribute("JWT is not supported", ex);
			throw ex;
		} catch (SignatureException ex) {
			req.setAttribute("Signature validation failed", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			req.setAttribute("invalid", ex.getMessage());
			throw ex;
		}
		return null;
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		return null;
	}

	public boolean validateClaims(Claims claims) throws AuthenticationException {
		try {
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			throw e;
		}
	}

	public Long extractExpiredTime(String token) {
		return (parseJwtClaims(token)).getExpiration().getTime();
	}
}
