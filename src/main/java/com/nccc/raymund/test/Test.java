package com.nccc.raymund.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@GetMapping("/test")
	public String helloWorld() {
		return "Hello, World!";
	}
}
