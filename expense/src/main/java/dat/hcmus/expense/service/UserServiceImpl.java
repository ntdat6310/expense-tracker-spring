package dat.hcmus.expense.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dat.hcmus.expense.entity.Role;
import dat.hcmus.expense.entity.User;
import dat.hcmus.expense.entity.UserModel;
import dat.hcmus.expense.exception.ItemAlreadyExistsException;
import dat.hcmus.expense.exception.ResourceNotFoundException;
import dat.hcmus.expense.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepo;
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	public UserServiceImpl(UserRepository userRepo, BCryptPasswordEncoder bcrypt) {
		super();
		this.userRepo = userRepo;
		this.bcrypt = bcrypt;
	}

	@Override
	public User createUser(UserModel uModel) {
		if (userRepo.existsByEmail(uModel.getEmail())) {
			throw new ItemAlreadyExistsException("User is already exist with email: " + uModel.getEmail());
		}
		User user = new User();
		BeanUtils.copyProperties(uModel, user);
		user.addRole(new Role("USER"));
		user.setPassword(bcrypt.encode((user.getPassword())));
		return userRepo.save(user);
	}

	@Override
	public User getUser(Long id) {
		Optional<User> result = userRepo.findById(id);
		if (result.isEmpty()) {
			throw new ResourceNotFoundException("User not found with id : " + id);
		}
		return result.get();
	}

	@Override
	public Page<User> getUsers(Pageable page) {
		return userRepo.findAll(page);
	}

	@Override
	public User update(UserModel user) {
		if (user.getEmail() != null && user.getEmail().compareTo(getLoggedInUser().getEmail()) != 0) {
			throw new ItemAlreadyExistsException("You can not change the email: " + user.getEmail());
		}
		User existingUser = getLoggedInUser();
		existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
		existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
		return userRepo.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = getUser(id);
		userRepo.delete(user);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found for the email: " + email);
		}
		return user;
	}
}
