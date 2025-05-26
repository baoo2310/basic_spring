package com.bank.bank_app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
			BankService bank = context.getBean(BankService.class);

			bank.deposit(100);
			bank.withdraw(40);
			System.out.println("Balance in USD: " + bank.getFormattedBalance());

			bank.setCurrency("VND");
            System.out.println("Same balance in VND: " + bank.getFormattedBalance());
			
			bank.deposit(500000);
			System.out.println("Balance after VND deposit: " + bank.getFormattedBalance());
			bank.setCurrency("USD");

			System.out.println("Final Balance in USD: " + bank.getFormattedBalance());
			
		}
	}

}
