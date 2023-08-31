package com.cartoonbbs.cartoonbbs.web;

import com.cartoonbbs.cartoonbbs.po.Tag;

import com.cartoonbbs.cartoonbbs.servive.ControllerService;
import com.cartoonbbs.cartoonbbs.servive.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;

    @Autowired
    private ControllerService controllerService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Tag> tags=tagService.listTagTop(10000);
        if(id==-1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",controllerService.listCartoon(id,pageable));
        model.addAttribute("activeTypeId",id);
        return "tags";
    }

}
