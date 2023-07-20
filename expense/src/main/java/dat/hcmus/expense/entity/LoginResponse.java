package dat.hcmus.expense.entity;

public class LoginResponse {
	private String email;
	private String accessToken;
	private Long expiredTime;

	public LoginResponse() {
		super();
	}

	public LoginResponse(String email, String accessToken, Long expiredTime) {
		super();
		this.email = email;
		this.accessToken = accessToken;
		this.expiredTime = expiredTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Long expiredTime) {
		this.expiredTime = expiredTime;
	}
}
