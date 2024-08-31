package com.booking.app.Booking.App.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> user = new HashSet<>();

    public void assignRoleToUser(User user){
        user.getRoles().add(this);
        this.getUser().add(user);
    }

    public void removeUserFromRole(User user){
        user.getRoles().remove(this);
        this.getUser().remove(user);
    }

    public void removeAllUsersFromRole(){
        if(this.getUser() != null){
            List<User> roleUsers = this.getUser().stream().toList();
            roleUsers.forEach(this :: removeUserFromRole);
        }
    }

    public String getName(){
        return name != null ? name : "";
    }
}

