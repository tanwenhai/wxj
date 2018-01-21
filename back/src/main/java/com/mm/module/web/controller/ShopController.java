package com.mm.module.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/shop")
public class ShopController {

    @GetMapping("/index")
    public String indexPage() {
        return "views/shop/index";
    }

    @GetMapping("/editor")
    public String editorPage() {
        return "views/shop/editor";
    }
}
