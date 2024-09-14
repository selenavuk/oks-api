package rs.oks.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.oks.api.service.UserService;

@SpringBootApplication
//@ComponentScan(basePackages = {"rs.oks.api"})
public class ApiServiceApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ApiServiceApplication.class);

		if (isRunningInProductionEnvironment()) {
			application.setAdditionalProfiles("production");
		} else {
			application.setAdditionalProfiles("develop");
		}

		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		userService.addDefaultAdminUser();
		userService.addTestUser();
	}

	private static boolean isRunningInProductionEnvironment() {
		String environment = System.getenv("ENVIRONMENT");
		return environment != null && environment.equals("production");
	}

}
