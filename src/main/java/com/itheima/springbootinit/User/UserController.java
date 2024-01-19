package com.itheima.springbootinit.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserDao userDao;

    // 根据名字查找用户
    @GetMapping("/getOne")
    public User getOne(@RequestParam("name") String name) {
        User user = userDao.findByName(name);
        return user;
    }

    // 得到数据库里的所有用户
    @GetMapping("/getAll")
    public List getAll() {
        List all = userDao.findAll();
        int len = all.size();
        for (int i = 0; i < len; i++) {
            System.out.println(all.get(i).toString());
        }
        return all;
    }

    // 添加一个用户
    @GetMapping("/add")
    public User add (@RequestParam("name") String name,
                     @RequestParam("age") int age,
                     @RequestParam("id") int id,
                     @RequestParam("password") String password) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setId(id);
        user.setPassword(password);
        User save = userDao.save(user);
        return save;
    }

    // 删除一个用户
    @GetMapping("/deleteOne")
    @Transactional
    public String deleteOne(@RequestParam("name") String name) {
        userDao.deleteByName(name);
        return "删除成功";
    }

    // 删除所有用户
    @GetMapping("/deleteAll")
    public String deleteAll() {
        userDao.deleteAll();
        return "全部删除成功";
    }
}
