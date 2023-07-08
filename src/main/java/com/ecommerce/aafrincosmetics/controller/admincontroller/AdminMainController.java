package com.ecommerce.aafrincosmetics.controller.admincontroller;


import com.ecommerce.aafrincosmetics.dto.request.CategoryRequestDto;
import com.ecommerce.aafrincosmetics.dto.request.ProductsRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.entity.Images;
import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.repo.CategoryRepo;
import com.ecommerce.aafrincosmetics.repo.ImagesRepo;
import com.ecommerce.aafrincosmetics.repo.ProductsRepo;
import com.ecommerce.aafrincosmetics.service.CategoryService;
import com.ecommerce.aafrincosmetics.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMainController {
    private final CategoryService categoryService;
    private final ProductsService productsService;
    private final ImagesRepo imagesRepo;
    private final CategoryRepo categoryRepo;
    private final ProductsRepo productsRepo;

    public static String UPLOAD_DIR = "src/main/resources/static/uploads";

    @GetMapping("/home")
    public String getAdminHomePage(Model model){
        return "adminPages/adminHome";
    }

    @GetMapping("/products")
    public String returnAdminHomePage(Model model){
        model.addAttribute("allProduct", productsService.getAllProducts());
        return "adminPages/productsHome";
    }

    @GetMapping("/add-product")
    public String getAddProductsPage(Model model, @ModelAttribute("errorMsg") String errorMsg){
        model.addAttribute("allCategory", categoryService.getAllCategory());
        model.addAttribute("categorydto", new CategoryRequestDto());
        model.addAttribute("productdto", new ProductsRequestDto());

        model.addAttribute("error", errorMsg);
        return "adminPages/addProduct";
    }

    @PostMapping("/add-product")
    public String addTheProduct(RedirectAttributes redirectAttributes, @ModelAttribute("categorydto") CategoryRequestDto categorydto,
                                @ModelAttribute("productdto") ProductsRequestDto productdto,
                                @RequestParam("image")MultipartFile file) throws IOException {
//        Uploading the image
        if(!file.isEmpty()){
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException err){
                System.out.println(err);
            }

            //Storing the path and other data's about image in database.
            String storedImageForThymeleaf = "/uploads/" + file.getOriginalFilename();

            Images newImage = new Images();
            newImage.setPath(storedImageForThymeleaf);
            newImage.setSize((int) file.getSize());
            newImage.setImageName(file.getOriginalFilename());


            //Handling the unique constraints violaiton
            try{
                //            Linking the product with image and category
                productdto.setImages(imagesRepo.save(newImage));
                productdto.setCategory(categoryRepo.findByCategoryName(categorydto.getCategoryName()));

                productsService.saveProductToTable(productdto);
            }catch (DataIntegrityViolationException e){
                redirectAttributes.addAttribute("errorMsg", "Product Name must be unique.");
                return "redirect:/admin/add-product";
            }

        }
        return "redirect:/admin/products";
    }

    //Update Product Controller
    @GetMapping("/update-product/{id}")
    public String getUpdatForm(Model model, @PathVariable Integer id){
        ProductsResponseDto foundProduct = productsService.getProductById(id);
        model.addAttribute("productdto",foundProduct );
        model.addAttribute("categorydto", categoryRepo.findByCategoryName(foundProduct.getCategory().getCategoryName()));
        model.addAttribute("allCategory", categoryService.getAllCategory());

        return "adminPages/updateProduct";

    }
    @PostMapping("/update-product/{id}")
    public String updateProduct(@PathVariable Integer id,@ModelAttribute("categorydto") CategoryRequestDto categorydto,
    @ModelAttribute("productdto") ProductsRequestDto productdto,
    @RequestParam("image")MultipartFile file) throws IOException {


        String storedImageForThymeleaf;
        Products foundProduct  = productsRepo.findById(id).get();


//        Uploading the image
        if(!file.isEmpty()){
            Path uploadPath = Paths.get(UPLOAD_DIR);

            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException err){
                System.out.println(err);
            }

            //Storing the path and other data's about image in database.
            storedImageForThymeleaf = "/uploads/" + file.getOriginalFilename();
        }
        else{
            storedImageForThymeleaf = foundProduct.getImages().getPath();
        }
        //Get the image from the image table by the name ]
        //WIth which it is sored in the images databse.

//        Finding the image in the image repo using the ID with which it's saved to in database'
        Images foundImage = imagesRepo.findById(foundProduct.getImages().getId()).get();
        foundImage.setPath(storedImageForThymeleaf);
        foundImage.setSize((int) file.getSize());
        foundImage.setImageName(file.getOriginalFilename());


//            Linking the product with image and category
        productdto.setImages(imagesRepo.save(foundImage));
        productdto.setCategory(categoryRepo.findByCategoryName(categorydto.getCategoryName()));

        productsService.updateTheProduct(id, productdto);

        return "redirect:/admin/products";

    }




    //Delete Product COntroller
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Integer id) throws IOException {
        ProductsResponseDto foundProduct = productsService.getProductById(id);
        productsService.deleteTheProduct(id);

        /*
        String pwd = System.getProperty("user.dir");

        Path filepath = Paths.get(foundProduct.getImages().getPath().trim()); */

//        filepath = filepath.replace("\\","\\\\");

//        File fileToDelete = new File(filepath);

     /*   if (Files.exists(filepath)){
            System.out.println("File Exists."); //Debug
//            boolean isDeleted = fileToDelete.delete();
                Files.delete(filepath);
                productsService.deleteTheProduct(id);
                System.out.println("Failed to delete the file.");
        }
        System.out.println("File to Delete:" + filepath);

        */

        return "redirect:/admin/products";
    }


}
