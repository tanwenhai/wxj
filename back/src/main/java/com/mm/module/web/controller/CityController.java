package com.mm.module.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/city")
public class CityController {

    @GetMapping("/index")
    public String indexPage() {
        return "views/city/index";
    }

    @GetMapping("/editor")
    public String editorPage() {
        return "views/city/editor";
    }
}
