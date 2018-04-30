package cn.guoyka.simplermybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SimplerMybatisApplication extends ServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SimplerMybatisApplication.class, args);
	}
}
