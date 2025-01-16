package com.permansys.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.permansys.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
	// 可擴展查詢方法，例如自定義查詢條件

	// 自定義 JPQL 查詢
	@Query("SELECT u FROM Employee u WHERE u.account LIKE %:account% AND u.department LIKE %:department% AND u.team LIKE %:team% AND u.status LIKE %:status% AND u.hireDate =	 :hireDate")
	List queryCase1(@Param("account") String account, @Param("department") String department,
			@Param("team") String team, @Param("status") String status, @Param("hireDate") LocalDate hireDate);

	// 查詢剛新增的資料
	Employee findByAccount(String account);

	@Query("SELECT e FROM Employee e WHERE e.account = :account AND e.password = :password")
	Optional<Employee> findByUsernameAndPassword(@Param("account") String account, @Param("password") String password);


}