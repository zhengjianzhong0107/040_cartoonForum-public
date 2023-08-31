package com.cartoonbbs.cartoonbbs.servive;

import com.cartoonbbs.cartoonbbs.dao.UserRepository;
import com.cartoonbbs.cartoonbbs.po.User;
import com.cartoonbbs.cartoonbbs.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUse(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, Md5Utils.code(password));
        return user;
    }
}
