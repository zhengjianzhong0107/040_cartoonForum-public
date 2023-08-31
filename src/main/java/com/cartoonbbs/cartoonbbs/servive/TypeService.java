package com.cartoonbbs.cartoonbbs.servive;


import com.cartoonbbs.cartoonbbs.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    //TODO 1 新增方法
    Type saveType(Type type);
    //TODO 2 查询
    Type getType(Long id);
    //TODO 3 分页查询
    Page<Type> listType(Pageable pageable);
    //TODO 4 修改
    Type updateType(Long id,Type type);
    //TODO 5 删除
    Type deleteType(Long id);
    //TODO 6 通过名称来查询type
    Type getTypeByName(String name);
    List<Type> listType();
    List<Type> listTypeTop(Integer size);
}
