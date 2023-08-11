package com.dgmf.entity;

import com.dgmf.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(
        name = "tbl_user",
        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "userUsername_unique", // Entity Attribute name
//                        columnNames = "username" // DB Column name
//                ),
                @UniqueConstraint(
                        name = "email_unique", // Entity Attribute name
                        columnNames = "email" // DB Column name
                )
        }
)
/* When Spring Security starts, and set up the application, it
uses a "userDetails" object which comes from the "UserDetails"
Interface that contains bunch of Methods. To use these Methods,
implementing "UserDetails" Interface is requested
*/
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_generator"
    )
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence_name",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    // Pay attention to the designation "username" which has a
    // special meaning for the "UserDetails" Interface
//    @Column(name = "username", nullable = false, length = 20)
//    private String userUsername;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    private boolean isActive = true;
    @Column(name = "date_created")
    // Hibernate will automatically take the current Timestamp of the JVM
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @Column(name = "last_updated")
    // Hibernate will automatically take the current Timestamp of the JVM
    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    /* "username" and "password"
    Two Very important attributes to redefine precisely to
    clarify things because of the implementation of the
    "UserDetails" Interface
    */
    @Override
    public String getUsername() {
        // return userEmail;
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
