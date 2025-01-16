package com.permansys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.permansys.dto.EmployeeRequest;
import com.permansys.entity.Employee;
import com.permansys.entity.EmployeeResponse;
import com.permansys.service.EmployeeService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class LoginController {

	private static final String SECRET_KEY = "AAAAB3NzaC1yc2EAAAADAQABAAACAQDN93Xe6woMOC4FoEHOgF5k5h6c1cgfTpJ2hQxakCAjcrsBZlYUr1jUiC5PthvRKhBf5t8Pi1O9csz6uxXROq4EdIZJj91dvVVg2Gtt+Ts89DjB9yW0r64V++mDIPeV3EInjaRXuFTIyI3JRy540Uq4JUdfftHWMUnelbHG4gc0iS2RLBOh+A6HbFv9DFM0R0acFDIjcVbSsKEWz1UEKAl2xa2zuYyU1l/btLXaGkkYF/x92hlw6FFAv5WVyOtUqpwsU/VPrplU6QBjWZ9golCz5DL/mAdtgt6K9wqr2Mgyxf8tI9umY8W6j1HVcaBNFPvGWEI32DN/1AD6a33w8Hlvpm0RJ8DAg+OpeOgjvCElqlNHkccpIpj9xMx1zhCGwBKSYyZVwNgtCk/eIwudz91avmIKBo4tuH1+I5eB2vxTytS1VitIk84XUihHcmH/BzOGvhPAd5H73oOnhwi/zR+A0PeUxiu5x59HGcBgAutHTdNVY4Qay00wGkyA2FbG2TBspCDKaAjvkAK4GJBCcxJc9n2Qwp5A2XYxC86gkkI1bozfPMhxOtpmH3HyDl3XJoblPp8Pr+kQu99B7JNuh5bZynIcG1aS4t4yLmu3IepDSKaGD5izLOe5EVo8CaaOYPMpReSDPN9AjGAcqy9sPjLq/0DcHCGuKKTSiazSgLmcYw=="; // JWT

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/loginCase1")
	public ResponseEntity<EmployeeResponse> login(@RequestBody EmployeeRequest loginRequest) {
		Optional<Employee> employee = employeeService.validateEmployee(loginRequest.getAccount(),
				loginRequest.getPassword());

		EmployeeResponse response = new EmployeeResponse();
		if (employee.isPresent()) {
			if (employee.get().getStatus().equals("Y")) {

				String token = generateToken(loginRequest.getAccount()); // 生成 JWT
				response.setCode(200);
				response.setMessage("登入成功");
				response.setToken(token);
				response.setEmployee(employee.get());
				return ResponseEntity.ok(response);
			} else {
				response.setCode(401);
				response.setMessage("登入失敗，帳號已停用");
				response.setToken("");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		} else {
			response.setCode(401);
			response.setMessage("登入失敗，帳號或密碼錯誤");
			response.setToken("");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	@GetMapping("/checkLogin")
	public ResponseEntity<Map<String, Object>> checkLogin(@RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.replace("Bearer ", "");
			Claims claims = decodeToken(token); // 驗證 JWT
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("account", claims.getSubject());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	private String generateToken(String account) {
		return Jwts.builder().setSubject(account).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 300000000)) // 單位毫秒 5分鐘
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	private Claims decodeToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

}
