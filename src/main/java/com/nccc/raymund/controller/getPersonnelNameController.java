package com.nccc.raymund.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class getPersonnelNameController {

	@GetMapping("/getPersonnelName")
	public String getPersonnelName() {
		return "Hello !";
	}
}
