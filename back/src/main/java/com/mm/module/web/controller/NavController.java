package com.mm.module.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tanwenhai@gusoftware.com
 */
@Controller
@RequestMapping("/admin/nav")
public class NavController {
    @GetMapping("/index")
    public String indexPage() {
        return "views/nav/index";
    }

    @GetMapping("/editor")
    public String addPage() {
        return "views/nav/editor";
    }
}
