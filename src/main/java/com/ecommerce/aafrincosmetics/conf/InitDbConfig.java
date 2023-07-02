package com.ecommerce.aafrincosmetics.conf;

import com.ecommerce.aafrincosmetics.entity.Category;

import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.entity.Role;
import com.ecommerce.aafrincosmetics.entity.User;
import com.ecommerce.aafrincosmetics.repo.CategoryRepo;
import com.ecommerce.aafrincosmetics.repo.RoleRepo;
import com.ecommerce.aafrincosmetics.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class InitDbConfig {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;


    @PostConstruct
    public void doEntries(){
        if(roleRepo.findAll().size() == 0){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role savedAdmin = roleRepo.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            Role savedUser = roleRepo.save(userRole);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User newAdmin  = new User();
            newAdmin.setFirst_name("Ram");
            newAdmin.setLast_name("Charan");
            newAdmin.setUsername("admin");
            newAdmin.setEmail("admin@gmail.com");
            newAdmin.setPassword(encoder.encode("admin"));
            newAdmin.setRoles(Arrays.asList(savedAdmin));
            userRepo.save(newAdmin);

            User nonAdminUser = new User();
            nonAdminUser.setFirst_name("test");
            nonAdminUser.setUsername("test");
            nonAdminUser.setPassword(encoder.encode("test"));
            nonAdminUser.setRoles(Arrays.asList(savedUser));
            userRepo.save(nonAdminUser);


//            Category newCategory = new Category();
//            newCategory.setName("Hair");
//            Category savedCat = categoryRepo.save(newCategory);
//
//            Products newProduct = new Products();
//            newProduct.setProductName("Naturo Earth Organic Cold Pressed Extra Virgin Coconut Oil 180 ml");
//            newProduct.setDescription("Coconut oil is a natural alternative for skin moisturizer. It helps increase hydration and reduces water loss in dry skin. Massaging regularly with coconut oil not only helps bring moisture but also protects skin from premature aging. The fatty acids in the oil can kill harmful pathogens, including bacteria and fungi, which could potentially help prevent infections. It is also excellent for hair, as it can strengthen hair roots and prevent hairfall. Apart from being beneficial for skin and hair, coconut oil can also be used for cooking, salad dressing and baking, just like any other oil. It is one of the super-foods which has tons of health benefits.");
//            newProduct.setPrice(500);
//            newProduct.setStock(10);
//            newProduct.setCategory(savedCat);
//            productRepo.save(newProduct);


        }
    }

}
