package com.cartoonbbs.cartoonbbs.servive;

import com.cartoonbbs.cartoonbbs.po.User;

public interface UserService {
    User checkUse(String username,String password);
}
