package de.coronavirus.imis.config.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.coronavirus.imis.domain.InstitutionType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.experimental.Accessors;

import de.coronavirus.imis.domain.InstitutionImpl;

@Entity
@Table(name = "users")
@Data
@Builder(toBuilder = true)
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotEmpty
    private String username;

    private String firstName;
    private String lastName;

    @JsonIgnore
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionImpl institution;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.institution.getType().name()),
                new SimpleGrantedAuthority(userRole.name()));
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public String getInstitutionId() {
        return institution.getId();
    }

    public InstitutionType getInstitutionType() {
        return institution.getType();
    }

    // TODO: MapStruct kommt nicht mit der fluent-accessor-Variante klar
    //       Entweder MapStruct die Accessors beibringen oder das Fluent aus der Annotation entfernen

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public InstitutionImpl getInstitution() {
        return institution;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setInstitution(InstitutionImpl institution) {
        this.institution = institution;
    }
}
