package com.permansys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PerManSysApplication extends SpringBootServletInitializer {
//要支援 WAR 部署，必須讓主類別繼承 SpringBootServletInitializer，並覆寫 configure 方法。

	public static void main(String[] args) {
		SpringApplication.run(PerManSysApplication.class, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PerManSysApplication.class);
    }
}
