package dat.hcmus.expense.service;

import java.sql.Date;
import java.util.Optional;

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

	@Override
	public Page<Expense> getAll(Pageable page) {
		return expenseRepo.findAll(page);
	}

	@Override
	public Expense getById(Long id) {
		Optional<Expense> result = expenseRepo.findById(id);
		if (result.isPresent()) {
			return result.get();
		}
		throw new ResourceNotFoundException("Expense is not found for the id - " + id);
	}

	@Override
	public void deleteById(Long id) {
		Expense expense = getById(id);
		expenseRepo.delete(expense);
	}

	@Override
	public Expense add(Expense expense) {
		return expenseRepo.save(expense);
	}

	@Override
	public Expense update(Expense expense) {
//		Expense existingExpense = getById(expense.getId());
//		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
//		existingExpense.setDescription(
//				expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
//		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
//		existingExpense
//				.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
//		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
//		return expenseRepo.save(existingExpense);

		getById(expense.getId());
		return expenseRepo.save(expense);
	}

	@Override
	public Page<Expense> getByCategory(String category, Pageable page) {
		return expenseRepo.findByCategory(category, page);
	}

	@Override
	public Page<Expense> getByNameKeyword(String keyword, Pageable page) {
		return expenseRepo.findByNameContaining(keyword, page);
	}

	@Override
	public Page<Expense> getByDateBetween(Date startDate, Date endDate, Pageable page) {
		return expenseRepo.findByDateBetween(startDate, endDate, page);
	}
}
