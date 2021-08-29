package com.mabu.MabuWebStore;



import com.mabu.MabuWebStore.uploadfile.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MabuWebStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MabuWebStoreApplication.class, args);
	}
		@Bean
		CommandLineRunner init(StorageService storageService) {
				return (args) -> {
//					storageService.deleteAll();
					storageService.init();
				};
			}
}
