package com.bnroll.tm.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc")
public class MVController {

	@GetMapping("/login")
	public String googleLoginUi() {
		return "login";
	}

}
