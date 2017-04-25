package com.smartshop.eshop.web.rest;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.payment.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Inject
	private PaymentService paymentService;

}
