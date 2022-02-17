package ru.gb.gbthymeleafwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbthymeleafwinter.entity.Product;
import ru.gb.gbthymeleafwinter.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    @GetMapping
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Product product;

        if (id != null) {
            product = productService.findById(id);
        } else {
            product = new Product();
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping
    public String saveProduct(Product product) {
        productService.save(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    @GetMapping("/cart")
    public String showCart(Model model){
        model.addAttribute("products", productService.showAllProductFromCart());
        return "cart-list";
    }

    @GetMapping("/addToCart")
    public String addProductToCart(@RequestParam(name = "id") Long id) {
        Product product = productService.findById(id);
        productService.addProductToCart(product);
        return "redirect:/product/all";
    }

    @GetMapping("/deleteFromCart")
    public String deleteProductFromCart(@RequestParam(name = "id") int id) {
        productService.deleteProductFromCart(id);
        return "redirect:/product/cart";
    }

}
