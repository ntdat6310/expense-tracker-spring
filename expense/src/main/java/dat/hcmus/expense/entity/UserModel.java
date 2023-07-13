package dat.hcmus.expense.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;

public class UserModel {
	private String name;

	@Email(message = "Email is not valid")
	private String email;

	@Length(min = 6, message = "Password must be at least 6 characters")
	private String password;

	private Integer age;

	public UserModel() {
		super();
	}

	public UserModel(String name, String email, String password, Integer age) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
