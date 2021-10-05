package com.doncorleone.dondelivery.dto;

import com.doncorleone.dondelivery.entities.User;
import com.doncorleone.dondelivery.resources.exceptions.Telephone;
import com.doncorleone.dondelivery.resources.exceptions.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatorio")
    private String firstName;
    private String lastName;

    @NotBlank(message = "Campo obrigatorio")
    @Email(message = "Informar e-mail válido")
    @UniqueValue(domainClass = User.class, fieldName = "email", message = "Email já cadastrado.")
    private String email;

    @Telephone(message = "Informar telefone válido")
    @NotBlank(message = "Campo obrigatorio")
    private String telephone;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String telephone) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
    }

    public UserDTO(User entity) {
        super();
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        telephone = entity.getTelephone();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
