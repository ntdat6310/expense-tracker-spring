package dat.hcmus.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import jakarta.validation.Valid;

@RestController
public class ExpenseController {
	@Autowired
	private ExpenseService expenseService;

	@GetMapping("/expenses")
	public Page<Expense> getAllExpenses(Pageable page) {
		return expenseService.getAll(page);
		// Or you can convert the results to List
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
	public Expense addExpense(@Valid @RequestBody Expense expense) {
		return expenseService.add(expense);
	}

	@PutMapping("/expense")
	public Expense updateExpense(@Valid @RequestBody Expense expense) {
		return expenseService.update(expense);
	}
}
