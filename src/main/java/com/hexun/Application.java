package com.hexun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * springboot 主程序
 * @author hexun
 *
 */
@SpringBootApplication
//@ImportResource({"classpath:es-job.xml"})
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

}
