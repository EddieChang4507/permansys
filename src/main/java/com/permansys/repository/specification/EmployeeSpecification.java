package com.permansys.repository.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.permansys.entity.Employee;

import jakarta.persistence.criteria.Predicate;

public class EmployeeSpecification {
	public static Specification<Employee> filterByCriteria(String account, String department, String team,
			String status, LocalDate hireDate) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (account != null && !account.isEmpty()) {
				predicates.add(cb.like(root.get("account"), "%" + account + "%"));
			}
			if (department != null && !department.isEmpty()) {
				predicates.add(cb.like(root.get("department"), "%" + department + "%"));
			}
			if (team != null && !team.isEmpty()) {
				predicates.add(cb.like(root.get("team"), "%" + team + "%"));
			}
			if (status != null && !status.isEmpty()) {
				predicates.add(cb.like(root.get("status"), "%" + status + "%"));
			}
			if (hireDate != null) {
				predicates.add(cb.equal(root.get("hireDate"), hireDate));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
