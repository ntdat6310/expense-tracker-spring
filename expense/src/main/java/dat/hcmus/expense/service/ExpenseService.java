package dat.hcmus.expense.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dat.hcmus.expense.entity.Expense;

public interface ExpenseService {
	Page<Expense> getAll(Pageable page);

	Expense getById(Long id);

	void deleteById(Long id);

	public Expense add(Expense expense);

	public Expense update(Expense expense);
}
