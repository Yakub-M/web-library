package com.yakub.weblibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private List<String> roles;

    // Getters and Setters
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
    public List<String> getRoles() {
      return roles;
    }
    public void setRoles(List<String> roles) {
      this.roles = roles;
    }
}

