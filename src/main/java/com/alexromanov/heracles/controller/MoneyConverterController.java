package com.alexromanov.heracles.controller;

import com.alexromanov.heracles.entity.ConvertMoneyForm;
import com.alexromanov.heracles.service.MoneyConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MoneyConverterController {
	private final MoneyConverterService moneyConverterService;

	@GetMapping(value = "/convert")
	public String convertForm(Model model) {
		model.addAttribute("convertMoneyForm", new ConvertMoneyForm());
		return "convert.html";
	}

	@PostMapping(value = "/convert")
	public String formatMoney(@Validated @ModelAttribute("convertMoneyForm") ConvertMoneyForm convertMoneyForm, Model model){
		String convertResult = moneyConverterService.formatMoney(convertMoneyForm.getValue());
		model.addAttribute("converted", convertResult);
		return "result.html";
	}
}
