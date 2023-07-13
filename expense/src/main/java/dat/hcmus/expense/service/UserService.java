package dat.hcmus.expense.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dat.hcmus.expense.entity.User;
import dat.hcmus.expense.entity.UserModel;

public interface UserService {
	User createUser(UserModel user);

	User getUser(Long id);

	Page<User> getUsers(Pageable page);

	User update(UserModel user);

	void deleteUser(Long id);
}
