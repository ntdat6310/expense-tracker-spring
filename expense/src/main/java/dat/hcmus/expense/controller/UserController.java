package dat.hcmus.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dat.hcmus.expense.entity.User;
import dat.hcmus.expense.entity.UserModel;
import dat.hcmus.expense.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/user/register")
	public ResponseEntity<User> createUser(@RequestBody @Valid UserModel uModel) {
		return new ResponseEntity<User>(userService.createUser(uModel), HttpStatus.CREATED);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
	}

	@GetMapping("/users")
	public List<User> getUser(Pageable page) {
		return userService.getUsers(page).toList();
	}

	@PutMapping("/user")
	public User updateUser(@RequestBody @Valid UserModel user) {
		return userService.update(user);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

}
