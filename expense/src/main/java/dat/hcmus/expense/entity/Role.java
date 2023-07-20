package dat.hcmus.expense.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_roles")
public class Role {
	@Id
	private String name;

	@ManyToMany(mappedBy = "roles")
	Set<User> users;

	public Role() {
		super();
	}

	public Role(String name, Set<User> users) {
		super();
		this.name = name;
		this.users = users;
	}
	
	public Role(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
