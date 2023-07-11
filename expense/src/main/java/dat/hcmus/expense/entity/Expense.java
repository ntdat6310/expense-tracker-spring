package dat.hcmus.expense.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_expenses")
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Expense name should not be null")
	@NotBlank(message = "Expense name should not blank")
	@Column(name = "expense_name")
	private String name;

	@NotNull(message = "Expense description should not be null")
	@Length(max = 300, message = "description must less than 300 characters")
	private String description;

	@Min(value = 0, message = "Amount must greate than or equal 0")
	@NotNull(message = "Amount should not be null")
	@Column(name = "expense_amount")
	private BigDecimal amount;

	@NotNull(message = "Expense description should not be null")
	@NotBlank(message = "Expense description should not blank")
	private String category;

	private Date date;

	@Column(name = "create_at", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp createAt;

	@Column(name = "update_at")
	@UpdateTimestamp
	private Timestamp updateAt;

	public Expense(Long id, String name, String description, BigDecimal amount, String category, Date date,
			Timestamp createAt, Timestamp updateAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.category = category;
		this.date = date;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Expense() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}
}
