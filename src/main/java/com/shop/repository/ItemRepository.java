package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository  extends JpaRepository<Item,Long>{

    List<Item> findByItemNm(String itemNm);
    //findBy변수명 - 조회하기

    List<Item> findByItemNmOrItemDetail(String itemNm,String itemDetail);
    //findBy변수명Or변수명 - 두가지 조회하기

    List<Item> findByPriceLessThan(Integer price);
    //findBy변수명LessThen(인수) - 변수명 < 인수

    List<Item>  findByPriceLessThanOrderByPriceDesc(Integer price);
    //findBy변수명LessThenOrderBy변수명DESC(인수) - 인수보다 작은 변수를 내림차순으로 정렬

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);


    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc",nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetil);

//    @Query(value="select * from item i where i.item_detail like " +
//            "%:itemDetail% order by i.price desc", nativeQuery = true)
//    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
