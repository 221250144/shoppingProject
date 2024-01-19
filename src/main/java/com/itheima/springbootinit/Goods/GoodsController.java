package com.itheima.springbootinit.Goods;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    GoodsDao goodsDao;

    @GetMapping("/getAllGoods")
    public List getAllGoods() {
        List all = goodsDao.findAll();
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }

    @Transactional
    @PostMapping("/updateGoodsImage")
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
        System.out.println(goods.toString());
        goods.setImagePath(fileName);

        System.out.println(fileName);
        return "images/" + fileName;
    }
}
