package com.spring.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@Column(name = "role_id")
	private int roleId;
	
	@Column(name = "role")
	private String role;
	
	@ManyToOne
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	private User user;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
