package com.egg.expertfinder.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String handleError() {
        return "";
    }

    @RequestMapping("/error/403")
    public String handle403Error() {
        return "error_403.html";
    }

    @RequestMapping("/error/404")
    public String handle404Error() {
        return "error_404.html";
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }

}
