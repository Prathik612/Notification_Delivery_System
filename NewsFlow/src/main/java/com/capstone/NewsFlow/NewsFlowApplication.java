package com.capstone.NewsFlow;

// import java.util.Arrays;
// import java.util.HashSet;

// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;

// import com.capstone.NewsFlow.models.User;
// import com.capstone.NewsFlow.services.UserService;

@SpringBootApplication
public class NewsFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsFlowApplication.class, args);
	}

	// @Bean
    // public CommandLineRunner demo(UserService userService) {
    //     return (args) -> {
    //         if (userService.findByUsername("admin") == null) {
    //             User admin = new User();
    //             admin.setUsername("admin");
    //             admin.setEmail("admin@example.com");
    //             admin.setPassword("admin123");
    //             userService.save(admin, new HashSet<>(Arrays.asList("ADMIN")));
    //         }
    //     };
    // }

}
