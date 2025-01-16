package com.permansys.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.permansys.dto.EmployeeRequest;
import com.permansys.entity.Employee;
import com.permansys.entity.EmployeeResponse;
import com.permansys.repository.EmployeeRepository;
import com.permansys.repository.specification.EmployeeSpecification;
import com.permansys.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	// 查詢所有使用者
	@GetMapping
	public List<Employee> getAllUsers() {
		return employeeService.getAllUsers();
	}

	// 根據帳號查詢單個使用者
	@GetMapping("/{account}")
	@ResponseBody
	public ResponseEntity<Employee> getUserByAccount(@PathVariable String account) {
		Employee employee = new Employee();
		employee.setAccount(account);
		employee.setName("John Doe");
		return ResponseEntity.ok(employee); // 回傳 JSON 格式資料
	}

	// 根據帳號查詢單個使用者
	@GetMapping("/queryCase1")
	@ResponseBody
	public ResponseEntity<List> queryCase1(@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "department", required = false) String department,
			@RequestParam(value = "team", required = false) String team,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "hireDate", required = false) LocalDate hireDate) {
		Specification<Employee> spec = EmployeeSpecification.filterByCriteria(account, department, team, status,
				hireDate);
		List<Employee> result = employeeRepository.findAll(spec);
		return ResponseEntity.ok(result); // 回傳 LIST 格式資料
	}

	// 根據帳號查詢單個使用者（只回傳第一筆）
	@PostMapping("/queryCase2")
	@ResponseBody
	public ResponseEntity<Employee> queryCase2(@RequestBody EmployeeRequest request) {
		// 查詢符合條件的資料（主鍵查詢）
		Optional<Employee> employeeOptional = employeeRepository.findById(request.getAccount());

		if (employeeOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 資料不存在時回傳 404
		}

		// 回傳第一筆資料（單一物件）
		return ResponseEntity.ok(employeeOptional.get());
	}

	@PostMapping("/insertCase1")
	public ResponseEntity<EmployeeResponse> insertCase1(@RequestBody EmployeeRequest request) {
		EmployeeResponse response = new EmployeeResponse();
		try {
			// 建立 Employee 實體
			Employee employee = new Employee();
			employee.setAccount(request.getAccount());
			employee.setPassword(request.getPassword());
			employee.setName(request.getName());
			if (request.getBirthdate() != null && !request.getBirthdate().isEmpty()) {
				employee.setBirthDate(LocalDate.parse(request.getBirthdate()));
			}
			employee.setDepartment(request.getDepartment());
			employee.setTeam(request.getTeam());
			employee.setStatus(request.getStatus());
			if (request.getEffectiveDate() != null && !request.getEffectiveDate().isEmpty()) {
				employee.setEffectiveDate(LocalDate.parse(request.getEffectiveDate()));
			}
			if (request.getDisabledDate() != null && !request.getDisabledDate().isEmpty()) {
				employee.setDisabledDate(LocalDate.parse(request.getDisabledDate()));
			}
			if (request.getHireDate() != null && !request.getHireDate().isEmpty()) {
				employee.setHireDate(LocalDate.parse(request.getHireDate()));
			}
			employee.setMemo(request.getMemo());

			// 儲存到資料庫
			employeeRepository.save(employee);

			// 準備成功回應
			response.setCode(200);
			response.setMessage("Insert successful");
			response.setEmployee(employee);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			// 準備失敗回應
			response.setCode(500);
			response.setMessage("Insert failed: " + e.getMessage());
			response.setEmployee(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/deleteCase1")
	public ResponseEntity<EmployeeResponse> deleteCase1(@RequestBody EmployeeRequest request) {
		EmployeeResponse response = new EmployeeResponse();
		try {
			Optional<Employee> employeeOptional = employeeRepository.findById(request.getAccount());
			Employee employee = employeeOptional.get();
			employee.setDisabledDate(LocalDate.now());
			employee.setStatus("N");

			// 儲存到資料庫
			employeeRepository.save(employee);

			// 準備成功回應
			response.setCode(200);
			response.setMessage("delete successful");
			response.setEmployee(employee);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			// 準備失敗回應
			response.setCode(500);
			response.setMessage("delete failed: " + e.getMessage());
			response.setEmployee(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/updateCase1")
	public ResponseEntity<EmployeeResponse> updateCase1(@RequestBody EmployeeRequest request) {
		EmployeeResponse response = new EmployeeResponse();
		try {
			// 建立 Employee 實體
			Employee employee = new Employee();
			employee.setAccount(request.getAccount());
			employee.setPassword(request.getPassword());
			employee.setName(request.getName());
			if (request.getBirthdate() != null && !request.getBirthdate().isEmpty()) {
				employee.setBirthDate(LocalDate.parse(request.getBirthdate()));
			}
			employee.setDepartment(request.getDepartment());
			employee.setTeam(request.getTeam());
			employee.setStatus(request.getStatus());
			if (request.getEffectiveDate() != null && !request.getEffectiveDate().isEmpty()) {
				employee.setEffectiveDate(LocalDate.parse(request.getEffectiveDate()));
			}
			if (request.getDisabledDate() != null && !request.getDisabledDate().isEmpty()) {
				employee.setDisabledDate(LocalDate.parse(request.getDisabledDate()));
			}
			if (request.getHireDate() != null && !request.getHireDate().isEmpty()) {
				employee.setHireDate(LocalDate.parse(request.getHireDate()));
			}
			employee.setMemo(request.getMemo());

			// 儲存到資料庫
			employeeRepository.save(employee);

			// 準備成功回應
			response.setCode(200);
			response.setMessage("Insert successful");
			response.setEmployee(employee);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			// 準備失敗回應
			response.setCode(500);
			response.setMessage("Insert failed: " + e.getMessage());
			response.setEmployee(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
