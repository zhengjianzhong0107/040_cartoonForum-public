package com.cartoonbbs.cartoonbbs.servive;

import com.cartoonbbs.cartoonbbs.NotFoundException;
import com.cartoonbbs.cartoonbbs.dao.CartoonRepository;
import com.cartoonbbs.cartoonbbs.po.Cartoon;
import com.cartoonbbs.cartoonbbs.po.Type;
import com.cartoonbbs.cartoonbbs.util.MarkdownUtils;
import com.cartoonbbs.cartoonbbs.vo.CartoonQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

//后端首页逻辑
@Service
public class ControllerImpl implements ControllerService {
    @Autowired
    private CartoonRepository cartoonRepository;
    @Override
    public Cartoon getCartoon(Long id) {
        return cartoonRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Cartoon getAndConvert(Long id) {
        Cartoon cartoon = cartoonRepository.findById(id).get();
        if (cartoon == null) {
            throw new NotFoundException("该帖子不存在");
        }
        Cartoon c = new Cartoon();
        BeanUtils.copyProperties(cartoon,c);
        String content = c.getContent();
        c.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        cartoonRepository.updateViews(id);
        return c;
    }

    @Override
    public Page<Cartoon> listCartoon(Pageable pageable, CartoonQuery cartoon) {

        return cartoonRepository.findAll(new Specification<Cartoon>() {
            @Override
            public Predicate toPredicate(Root<Cartoon> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                //动态查询条件
                //TODO 1 查询标题
                if(!"".equals(cartoon.getTitle()) && cartoon.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+cartoon.getTitle()+"%"));
                }
                //TODO 2 查询分类
                if(cartoon.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),cartoon.getTypeId()));
                }
                //TODO 3 是否推荐
                if(cartoon.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),cartoon.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Cartoon> listCartoon(Pageable pageable) {
        return cartoonRepository.findAll(pageable);
    }

    @Override
    public Page<Cartoon> listCartoon(Long tagId, Pageable pageable) {
        return cartoonRepository.findAll(new Specification<Cartoon>() {
            @Override
            public Predicate toPredicate(Root<Cartoon> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join= root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Cartoon> listCartoon(String query, Pageable pageable) {
        return cartoonRepository.findByQuery(query,pageable);
    }


    @Override
    public List<Cartoon> listRecommendCartoonTop(Integer size) {
        Pageable pageable= PageRequest.of(0,size, Sort.by(Sort.Direction.DESC, "updateTime"));
        return cartoonRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Cartoon>> archiveCartoon() {
        List<String> years=cartoonRepository.findGroupYear();
        Map<String,List<Cartoon>> map=new HashMap<>();
        for(String year:years){
            map.put(year,cartoonRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countCartoon() {
        return cartoonRepository.count();
    }

    @Transactional
    @Override
    public Cartoon saveCartoon(Cartoon cartoon) {
        if(cartoon.getId()==null){
            cartoon.setCreateTime(new Date());
            cartoon.setUpdateTime(new Date());
            cartoon.setViews(0);
        }else {
            cartoon.setUpdateTime(new Date());
        }
        return cartoonRepository.save(cartoon);
    }
    @Transactional
    @Override
    public Cartoon updateCartoon(Long id, Cartoon cartoon) {
        Cartoon c = cartoonRepository.findById(id).get();
        if (c==null){
            throw new NotFoundException("该帖子不存在");
        }
        BeanUtils.copyProperties(c,cartoon);
        return cartoonRepository.save(c);
    }
    @Transactional
    @Override
    public void deleteCartoon(Long id) {
        cartoonRepository.deleteById(id);

    }
}
