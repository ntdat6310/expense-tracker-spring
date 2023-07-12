package dat.hcmus.expense.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/expenses/category")
	public List<Expense> getByCategory(@RequestParam String category, Pageable page) {
		return expenseService.getByCategory(category, page).toList();
	}

	@GetMapping("/expenses/name")
	public List<Expense> getByName(@RequestParam String keyword, Pageable page) {
		return expenseService.getByNameKeyword(keyword, page).toList();
	}

	@GetMapping("/expenses/date")
	public List<Expense> getByDate(@RequestParam(required = true, name = "startDate") Date startDate,
			@RequestParam(required = true, name = "endDate") Date endDate, Pageable page) {
		System.out.println(startDate.toString());
		System.out.println(endDate.toString());
		return expenseService.getByDateBetween(startDate, endDate, page).toList();
	}
}
