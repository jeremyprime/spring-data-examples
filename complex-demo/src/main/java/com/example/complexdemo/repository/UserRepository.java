package com.example.complexdemo.repository;

import com.example.complexdemo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findByName(String name);

    List<User> findByEmail(String email);

}
