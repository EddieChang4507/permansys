package com.permansys.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@Column(name = "account", length = 50, nullable = false)
	private String account; // 帳號

	@Column(name = "password", length = 255, nullable = false)
	private String password; // 密碼

	@Column(name = "name", length = 100, nullable = false)
	private String name; // 姓名

	@Column(name = "birth_date")
	private LocalDate birthDate; // 生日

	@Column(name = "department", length = 100)
	private String department; // 部門

	@Column(name = "team", length = 100)
	private String team; // 組別

	@Column(name = "status", length = 20)
	private String status; // 狀態

	@Column(name = "effective_date")
	private LocalDate effectiveDate; // 帳號生效日期

	@Column(name = "disabled_date")
	private LocalDate disabledDate; // 帳號停用日期

	@Column(name = "hire_date")
	private LocalDate hireDate; // 入職日期

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt; // 建立日期

	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 修改日期

	@Column(name = "memo")
	private String memo; // 備註

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	// Getters and Setters
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getDisabledDate() {
		return disabledDate;
	}

	public void setDisabledDate(LocalDate disabledDate) {
		this.disabledDate = disabledDate;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
