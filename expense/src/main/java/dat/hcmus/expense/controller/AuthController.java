package dat.hcmus.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dat.hcmus.expense.entity.LoginRequest;
import dat.hcmus.expense.entity.LoginResponse;
import dat.hcmus.expense.entity.User;
import dat.hcmus.expense.security.JwtUtil;
import jakarta.validation.Valid;

@RestController
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginReq) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

		User user = (User) authentication.getPrincipal();
		String accessToken = jwtUtil.createToken(user);
		LoginResponse loginRes = new LoginResponse(user.getEmail(), accessToken,
				jwtUtil.extractExpiredTime(accessToken));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new ResponseEntity<LoginResponse>(loginRes, HttpStatus.OK);
	}
}
