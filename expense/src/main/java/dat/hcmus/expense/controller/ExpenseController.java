package dat.hcmus.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dat.hcmus.expense.entity.Expense;
import dat.hcmus.expense.service.ExpenseService;

@RestController
public class ExpenseController {
	@Autowired
	private ExpenseService expenseService;

	@GetMapping("/expenses")
	public List<Expense> getAllExpenses() {
		return expenseService.getAll();
	}

	@GetMapping("/expense/{id}")
	public Expense getExpenseById(@PathVariable Long id) {
		return expenseService.getById(id);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expense/{id}")
	public void deleteExpenseById(@PathVariable Long id) {
		expenseService.deleteById(id);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expense")
	public Expense addExpense(@RequestBody Expense expense) {
		return expenseService.add(expense);
	}

	@PutMapping("/expense")
	public Expense updateExpense(@RequestBody Expense expense) {
		return expenseService.update(expense);
	}
}
