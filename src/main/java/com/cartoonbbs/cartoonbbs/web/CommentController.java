package com.cartoonbbs.cartoonbbs.web;

import com.cartoonbbs.cartoonbbs.po.Comment;
import com.cartoonbbs.cartoonbbs.po.User;
import com.cartoonbbs.cartoonbbs.servive.CommentService;
import com.cartoonbbs.cartoonbbs.servive.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private ControllerService controllerService;

  @Value("${comment.avatar}")
    private String avatar;


    @GetMapping("/comments/{cartoonId}")
    public String comments( @PathVariable Long cartoonId, Model model) {
        model.addAttribute("comments", commentService.listCommentByCartoonId(cartoonId));

        return "details :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long cartoonId = comment.getCartoon().getId();
        comment.setCartoon(controllerService.getCartoon(cartoonId));
        User user= (User) session.getAttribute("user");
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);
        return "redirect:/comments/" + cartoonId;
    }


}
