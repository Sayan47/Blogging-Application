package com.sayan.blog.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

	@Id
	@Column(nullable = false)
	private String roleId;
	@Column(name = "role_name", nullable = false, length = 50)
	private String name;

//	@ManyToMany(mappedBy = "roles")
//	private Set<User> users;

}
