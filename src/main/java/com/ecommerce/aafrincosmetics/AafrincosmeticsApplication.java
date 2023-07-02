package com.ecommerce.aafrincosmetics;

import com.ecommerce.aafrincosmetics.entity.Category;
import com.ecommerce.aafrincosmetics.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableAsync
@SpringBootApplication
@RequiredArgsConstructor
public class AafrincosmeticsApplication implements CommandLineRunner {

	private final CategoryRepo categoryRepo;

	public static void main(String[] args) {
		SpringApplication.run(AafrincosmeticsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Category cat1 = new Category();
//		cat1.setCategoryName("Hair");
//		categoryRepo.save(cat1);
//
//		Category cat2 = new Category();
//		cat2.setCategoryName("Make-Up");
//		categoryRepo.save(cat2);
//
//		Category cat3 = new Category();
//		cat3.setCategoryName("Moisturizer");
//		categoryRepo.save(cat3);
	}
}
