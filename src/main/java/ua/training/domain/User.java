package ua.training.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"login", "id", "email"})})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(name = "login",  unique = true)
    private String login;

    @Column(name = "email",  unique = true)
    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.ROLE_USER;

//    @OneToMany(mappedBy = "owner")
//    private List<Order> orders = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Receipt> receipts = new ArrayList<>();
//
//    @ManyToMany(cascade = CascadeType.REFRESH)
//    @JoinTable(name = "user_card",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "card_id"))
//    private Set<BankCard> cards = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> result = new HashSet<>();
        result.add(getRole());
        return result;
    }

    @Override
    public String getUsername() {
        return getLogin();
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
