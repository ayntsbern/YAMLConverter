package com.ayntsbern.YAMLConverter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConverterController {

    @RequestMapping("/")
    public String getHomePage() {
        return "converter.html";
    }
}