package dat.hcmus.expense.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dat.hcmus.expense.entity.Expense;
import dat.hcmus.expense.exception.ResourceNotFoundException;
import dat.hcmus.expense.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	@Autowired
	private ExpenseRepository expenseRepo;
	@Autowired
	private UserService userService;

	@Override
	public Page<Expense> getAll(Pageable page) {
		return expenseRepo.findByAuthorId(userService.getLoggedInUser().getId(), page);
	}

	@Override
	public Expense getById(Long id) {
		Expense result = expenseRepo.findByAuthorIdAndId(userService.getLoggedInUser().getId(), id);
		if (result == null) {
			throw new ResourceNotFoundException("Expense is not found for the id - " + id);
		}
		return result;
	}

	@Override
	public void deleteById(Long id) {
		Expense expense = getById(id);
		expenseRepo.delete(expense);
	}

	@Override
	public Expense add(Expense expense) {
		expense.setAuthor(userService.getLoggedInUser());
		return expenseRepo.save(expense);
	}

	@Override
	public Expense update(Expense expense) {
		Expense existingExpense = getById(expense.getId());
		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
		existingExpense.setDescription(
				expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
		existingExpense
				.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
		return expenseRepo.save(existingExpense);
	}

	@Override
	public Page<Expense> getByCategory(String category, Pageable page) {
		return expenseRepo.findByAuthorIdAndCategory(userService.getLoggedInUser().getId(), category, page);
	}

	@Override
	public Page<Expense> getByNameKeyword(String keyword, Pageable page) {
		return expenseRepo.findByAuthorIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page);
	}

	@Override
	public Page<Expense> getByDateBetween(Date startDate, Date endDate, Pageable page) {
		return expenseRepo.findByAuthorIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate,
				page);
	}
}
