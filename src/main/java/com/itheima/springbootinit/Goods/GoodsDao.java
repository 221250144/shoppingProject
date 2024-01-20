package com.itheima.springbootinit.Goods;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsDao extends JpaRepository<Goods, Integer> {
    Goods findByName(String goodsName);

    List findByType(GoodsType type);

    List findByStatus(boolean status);
}
