package com.cartoonbbs.cartoonbbs.dao;

import com.cartoonbbs.cartoonbbs.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);

}
