package com.example.complexdemo.model;

import org.springframework.data.annotation.Id;
import io.valkey.springframework.data.valkey.core.ValkeyHash;
import io.valkey.springframework.data.valkey.core.index.Indexed;

@ValkeyHash("users")
public class User {

    @Id
    private String id;

    @Indexed
    private String name;

    @Indexed
    private String email;

    private int age;

    public User() {}

    public User(String id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "', email='" + email + "', age=" + age + "}";
    }

}
