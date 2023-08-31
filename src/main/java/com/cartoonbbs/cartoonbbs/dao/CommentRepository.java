package com.cartoonbbs.cartoonbbs.dao;


import com.cartoonbbs.cartoonbbs.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByCartoonId(Long cartoonId, Sort sort);

    List<Comment> findByCartoonIdAndParentCommentNull(Long cartoonId, Sort sort);
}

