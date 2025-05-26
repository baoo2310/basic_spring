package com.bank.bank_app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
// import org.springframework.stereotype.Component;

@Configuration
@ComponentScan({"com.bank", "com.bank.bank_app.notification"})
public class AppConfig {

}
