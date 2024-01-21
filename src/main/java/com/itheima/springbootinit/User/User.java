package com.itheima.springbootinit.User;

import com.itheima.springbootinit.Goods.Goods;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Entity
@Table(name = "user")
public class User {
    @Id
    private String name;
    private String password;
    private int age;
    private int id;

    public User() {
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "id: " + id + ", name: " + name + ", password: " + password + ", age: " + age;
    }
}
