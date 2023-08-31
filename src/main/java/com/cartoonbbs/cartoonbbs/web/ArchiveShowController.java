package com.cartoonbbs.cartoonbbs.web;

import com.cartoonbbs.cartoonbbs.servive.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArchiveShowController {

    @Autowired
    private ControllerService controllerService;

    @GetMapping("/archives")
    public  String archives(Model model){

    model.addAttribute("archiveMap",controllerService.archiveCartoon());
    model.addAttribute("cartoonCount",controllerService.countCartoon());
        return "archives";

    }
}
