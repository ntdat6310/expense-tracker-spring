package dat.hcmus.expense.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dat.hcmus.expense.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
//	@Query("SELECT e FROM Expense e WHERE e.author.id=:userId AND e.category=:category")
	Page<Expense> findByAuthorIdAndCategory(Long authorId, String category, Pageable page);

	Page<Expense> findByAuthorIdAndNameContaining(Long authorId, String keyword, Pageable page);

	Page<Expense> findByAuthorIdAndDateBetween(Long authorId, Date startDate, Date endDate, Pageable page);

	Page<Expense> findByAuthorId(Long authorId, Pageable page);

	Expense findByAuthorIdAndId(Long expenseId, Long authorId);
}
