package by.barzov.test1.controllers;

import by.barzov.test1.aspect.Profiling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profiling
public class MainController
{
    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("title", "Страница про нас");
        return "about";
    }
}
