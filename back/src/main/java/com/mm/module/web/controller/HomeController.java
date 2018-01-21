package com.mm.module.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tanwenhai@gusoftware.com
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String indexPage() {
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/index")

    public String adminIndexPage() {
        return "views/user/index";
    }
}
