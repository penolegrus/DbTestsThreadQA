package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Главная страница
 */
@Controller
public class IndexController {
    @GetMapping(path = "/")
    public String index() {
        return "/index";
    }
}
