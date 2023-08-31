package com.cartoonbbs.cartoonbbs.web;

import com.cartoonbbs.cartoonbbs.po.Type;
import com.cartoonbbs.cartoonbbs.servive.ControllerService;
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

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private ControllerService controllerService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Type> types=typeService.listTypeTop(10000);
        if(id==-1){
            id=types.get(0).getId();
        }
        CartoonQuery cartoonQuery=new CartoonQuery();
        cartoonQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",controllerService.listCartoon(pageable,cartoonQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
