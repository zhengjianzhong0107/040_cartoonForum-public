package com.cartoonbbs.cartoonbbs.dao;

import com.cartoonbbs.cartoonbbs.po.Cartoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//操作数据库
public interface CartoonRepository extends JpaRepository<Cartoon,Long>, JpaSpecificationExecutor<Cartoon> {
    @Query("select c from Cartoon  c where  c.recommend=true ")
    List<Cartoon> findTop(Pageable pageable);

    @Query("select c from Cartoon c where c.title like ?1 or c.content like ?1")
    Page<Cartoon> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Cartoon  c set c.views=c.views+1 where c.id=?1")
    int updateViews(Long id);

    @Query("select function('date_format',c.updateTime,'%Y') as year from  Cartoon  c group by function('date_format',c.updateTime,'%Y') order by  year desc ")
    List<String> findGroupYear();

    @Query("select c from  Cartoon  c where function('date_format',c.updateTime,'%Y')= ?1 ")
    List<Cartoon> findByYear(String year);


}
