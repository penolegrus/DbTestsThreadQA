package controllers;

import entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.ProductService;


/**
 * Страницы с продуктами
 */
@Controller("/products")
public class ProductController {
    /**
     * Сервис с продуктами, для чтения из БД
     */
    @Autowired
    private ProductService productService;

    /**
     * Получить все продукты
     * @param model модель продукта
     * @return
     */
    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", productService.listAllProducts());
        return "products";
    }

    /**
     * Получить продукт по его айди
     * @param id айди продукта
     * @param model модель продукта
     * @return
     */
    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "productshow";
    }

    /**
     * Редактировать продукт
     * @param id айди продукта
     * @param model модель продукта
     * @return
     */
    @GetMapping("/product/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "productform";
    }

    /**
     * Создать новый продукт
     * @param model модель продукта
     * @return
     */
    @RequestMapping("product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "productform";
    }

    /**
     * Сохранить продукт
     * @param product модель продукта
     * @return
     */
    @RequestMapping(path = "product", method = RequestMethod.POST)
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/product/" + product.getId();
    }

    /**
     * Удалить продукт
     * @param id айди продукта
     * @return
     */

    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

}
