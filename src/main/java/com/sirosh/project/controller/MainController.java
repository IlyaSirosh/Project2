package com.sirosh.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Illya on 4/12/17.
 */

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String main() {
        return "src/index.html";
    }

}
