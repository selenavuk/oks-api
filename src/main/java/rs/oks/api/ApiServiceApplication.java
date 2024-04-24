package rs.oks.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"rs.oks.api"})
public class ApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ApiServiceApplication.class);

		if (isRunningInProductionEnvironment()) {
			application.setAdditionalProfiles("production");
		} else {
			application.setAdditionalProfiles("develop");
		}

		application.run(args);
	}

	private static boolean isRunningInProductionEnvironment() {
		String environment = System.getenv("ENVIRONMENT");
		return environment != null && environment.equals("production");
	}

}
