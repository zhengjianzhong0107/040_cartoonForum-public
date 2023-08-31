package com.cartoonbbs.cartoonbbs.web;

import com.cartoonbbs.cartoonbbs.servive.ControllerService;
import com.cartoonbbs.cartoonbbs.servive.TagService;
import com.cartoonbbs.cartoonbbs.servive.TypeService;
import com.cartoonbbs.cartoonbbs.vo.CartoonQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired
    private ControllerService controllerService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",controllerService.listCartoon(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendCartoon",controllerService.listRecommendCartoonTop(8));
        return "index";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",controllerService.listCartoon("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";

    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        //model.addAttribute("cartoon",controllerService.getCartoon(id));
        model.addAttribute("cartoon",controllerService.getAndConvert(id));
        return "details";
    }

}
