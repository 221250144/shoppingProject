package com.itheima.springbootinit.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
    public void deleteByName(String name);

    User findByName(String name);
}
