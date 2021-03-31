package se.lexicon.thymeleaf_workshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se.lexicon.thymeleaf_workshop.dto.ProductDetailsDto;
import se.lexicon.thymeleaf_workshop.dto.ProductDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductManagementController {

    List<ProductDto> productDtoList;

    @PostConstruct
    public void init() {

        if (productDtoList == null) productDtoList = new ArrayList<>();

        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setName("Test Product 1");
        productDto.setPrice(10);
        ProductDetailsDto productDetailsDto = new ProductDetailsDto();
        productDetailsDto.setId(1);
        productDetailsDto.setName("Test");
        productDetailsDto.setImageString("/images/oca.png");
        productDetailsDto.setDescription("Test Description");

        productDto.setProductDetailsDto(productDetailsDto);

        productDtoList.add(productDto);


        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2);
        productDto2.setName("Test Product 2");
        productDto2.setPrice(20);
        ProductDetailsDto productDetailsDto2 = new ProductDetailsDto();
        productDetailsDto2.setId(1);
        productDetailsDto2.setName("Test2");
        productDetailsDto2.setImageString("/images/ocp.jpg");

        productDetailsDto2.setDescription("Test Description 2");

        productDto2.setProductDetailsDto(productDetailsDto2);

        productDtoList.add(productDto2);

    }

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("productDtoList", productDtoList);
        return "productManagement";
    }

    @GetMapping("/addForm")
    public String registerForm(Model model){
        ProductDto dto= new ProductDto();
        model.addAttribute("dto", dto);
        return"productAddForm";
    }

    @GetMapping("/find/{id}")
    public String getProductById(@PathVariable("id")Integer id, Model model){
        System.out.println("id = " + id);
        Optional<ProductDto> optionalProductDto = productDtoList.
                stream().
                filter(productDto -> productDto.getId()==id).
                findFirst();
        if (optionalProductDto.isPresent())
            model.addAttribute("productDto", optionalProductDto.get());
        else
            model.addAttribute("productDto", new ProductDto());
        return "adminProductDetails";
    }
}
