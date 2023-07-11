package dat.hcmus.expense.service;

import java.util.List;

import dat.hcmus.expense.entity.Expense;

public interface ExpenseService {
	List<Expense> getAll();

	Expense getById(Long id);

	void deleteById(Long id);

	public Expense add(Expense expense);

	public Expense update(Expense expense);
}
