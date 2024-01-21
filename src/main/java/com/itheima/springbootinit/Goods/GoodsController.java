package com.itheima.springbootinit.Goods;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    GoodsDao goodsDao;

    @GetMapping("/getAllGoods") // 得到所有商品
    public List getAllGoods() {
        List all = goodsDao.findAll();
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }

    @GetMapping("/getByType") // 根据商品类型查找商品
    public List getByType(@RequestParam("type") GoodsType type) {
        List all = goodsDao.findByType(type);
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }

    @GetMapping("/addGoods") // 添加商品
    public Goods addGoods(@RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("price") int price,
                          @RequestParam("type") GoodsType type) {
        Goods goods = new Goods();
        goods.setName(name);
        goods.setDescription(description);
        goods.setPrice(price);
        goods.setType(type);
        Goods save = goodsDao.save(goods);
        return save;
    }
    @GetMapping("changeStatus") // 将商品放入或取出购物车
    public String changeStatus(@RequestParam("name") String name) {
        Goods goods = goodsDao.findByName(name);
        goods.setStatus(!goods.getStatus());
        goodsDao.save(goods);
        return "修改成功";
    }
    @GetMapping("getByName") // 根据商品名字查找商品
    public Goods getByName(@RequestParam("name") String name) {
        Goods goods = goodsDao.findByName(name);
        return goods;
    }
    @GetMapping("getByStatus") // 查看购物车里的商品 或 未放入购物车的商品
    public List getByStatus(@RequestParam("status") boolean status) {
        List all = goodsDao.findByStatus(status);
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }

    @Transactional
    @PostMapping("/updateGoodsImage") // 更新商品图片
    public String updateGoodsImage(MultipartFile file, String goodsName) {
        goodsName = URLDecoder.decode(goodsName, StandardCharsets.UTF_8);
        // file检验
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 重命名文件
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = goodsName + suffixName;
        // 文件上传路径
        String filePath = "C:\\Users\\86139\\Desktop\\springboot-init\\src\\main\\resources\\static\\images\\" + fileName;
        try {
            // 保存文件
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 更新数据库
        Goods goods = goodsDao.findByName(goodsName);
//        System.out.println(goods.toString());
        String webPath = "http://localhost:8080/images/" + fileName;
        goods.setImagePath(webPath);

        System.out.println(fileName);
        return "images/" + fileName;
    }
}
