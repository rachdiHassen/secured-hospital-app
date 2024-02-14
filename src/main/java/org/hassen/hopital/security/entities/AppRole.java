package org.hassen.hopital.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length =15)
    private String roleName;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
   // @ToString.Exclude
    private List<AppUsers> appUsers=new ArrayList<>();
}
