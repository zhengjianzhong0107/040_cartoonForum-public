package com.cartoonbbs.cartoonbbs.servive;

import com.cartoonbbs.cartoonbbs.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment>  listCommentByCartoonId(Long cartoonId);

    Comment saveComment(Comment comment);
}
