package com.itheima.springbootinit.Goods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsDao extends JpaRepository<Goods, Integer> {
    Goods findByName(String goodsName);

}
