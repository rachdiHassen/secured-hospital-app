package org.hassen.hopital.security.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppUsers {
    @Id
    private String userID;
    @Column(unique = true )
    private String userName;
    private String password;
    private boolean active;
    @ManyToMany( mappedBy = "appUsers", fetch = FetchType.EAGER)
    private List<AppRole> appRoles=new ArrayList<>();
}
