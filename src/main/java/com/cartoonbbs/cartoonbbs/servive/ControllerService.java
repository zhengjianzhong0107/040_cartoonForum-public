package com.cartoonbbs.cartoonbbs.servive;

import com.cartoonbbs.cartoonbbs.po.Cartoon;
import com.cartoonbbs.cartoonbbs.vo.CartoonQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ControllerService {
    Cartoon  getCartoon(Long id);

    Cartoon getAndConvert(Long id);

    Page<Cartoon> listCartoon(Pageable pageable, CartoonQuery cartoon);

    Page<Cartoon> listCartoon(Pageable pageable);

    Page<Cartoon> listCartoon(Long tagId,Pageable pageable);

    Page<Cartoon> listCartoon(String query,Pageable pageable);



    List<Cartoon > listRecommendCartoonTop(Integer size);

    Map<String,List<Cartoon>> archiveCartoon();

    Long countCartoon();

    Cartoon saveCartoon(Cartoon cartoon);

    Cartoon updateCartoon(Long id,Cartoon cartoon);

    void deleteCartoon(Long id);


}
