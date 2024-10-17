package com.aga.jtecrudform;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.Instant;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/createForm")
    public String from(Model model) {
        Product product = new Product();  // Assume you're getting the product here
        model.addAttribute("product", product);
        return "create-product";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid Product product, Model model) {
        log.info("Saving Product: {}", product);
        product.setCreatedAt( Instant.now() );
        productRepository.save(product);
        model.addAttribute("message", "Product information saved successfully!");
        return "create-product";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {
        Product product = (Product) ex.getBindingResult().getTarget();
        model.addAttribute("product", product);
        model.addAttribute("error", "Please fill out all required fields.");
        log.info("Product Validation failed for: {}", product);
        return "create-product";
    }

}
