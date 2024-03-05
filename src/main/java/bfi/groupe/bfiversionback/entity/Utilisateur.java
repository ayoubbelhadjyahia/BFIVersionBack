package bfi.groupe.bfiversionback.entity;

import bfi.groupe.bfiversionback.configuration.GrantedAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Utilisateur implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Username;
    private String email;
    private String Password;
    private Long Phonenumber;
    private String CodeVerification;
    @Enumerated(EnumType.STRING)
    private Role Role;
    private LocalDateTime DateEndCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Token> user_id;
    @JsonDeserialize(contentUsing = GrantedAuthorityDeserializer.class)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Role.getAuthorities();
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
