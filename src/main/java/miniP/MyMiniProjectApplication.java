package miniP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyMiniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMiniProjectApplication.class, args);
	}

}
