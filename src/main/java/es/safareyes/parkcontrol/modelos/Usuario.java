package es.safareyes.parkcontrol.modelos;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(schema = "parkcontrol", name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "contrasenya")
    private String contrasenya;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    public String getPassword() {
        return contrasenya;
    }

    public String getUsername() {
        return nombre;
    }

    public boolean isAccountNonExpired()     { return true; }
    public boolean isAccountNonLocked()      { return true; }
    public boolean isCredentialsNonExpired() { return true; }
    public boolean isEnabled()               { return true; }
}
