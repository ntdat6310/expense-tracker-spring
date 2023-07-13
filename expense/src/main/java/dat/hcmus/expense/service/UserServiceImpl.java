package dat.hcmus.expense.service;

import java.util.Optional;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dat.hcmus.expense.entity.User;
import dat.hcmus.expense.entity.UserModel;
import dat.hcmus.expense.exception.ItemAlreadyExistsException;
import dat.hcmus.expense.exception.ResourceNotFoundException;
import dat.hcmus.expense.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepo;

	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public User createUser(UserModel uModel) {
		if (userRepo.existsByEmail(uModel.getEmail())) {
			throw new ItemAlreadyExistsException("User is already exist with email: " + uModel.getEmail());
		}
		User user = new User();
		BeanUtils.copyProperties(uModel, user);
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
		User existingUser = userRepo.findByEmail(user.getEmail());
		if (existingUser == null) {
			throw new ResourceNotFoundException("User not found by email : " + user.getEmail());
		}
		existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
		existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
		existingUser.setPassword(user.getPassword() != null ? user.getPassword() : existingUser.getPassword());
		existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());

		return userRepo.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = getUser(id);
		userRepo.delete(user);
	}
}
