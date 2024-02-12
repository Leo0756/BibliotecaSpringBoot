package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "BIBLIOTECA")
public class EntityUsuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @NotEmpty(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 40, message = "El nombre tiene que tener entre 2 y 40 caracteres.")
    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;
    @Basic
    @NotEmpty(message = "El apellido no puede estar vacío.")
    @Size(min = 2, max = 40, message = "El nombre tiene que tener entre 2 y 40 caracteres.")
    @Column(name = "apellidos", nullable = false, length = 40)
    private String apellidos;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Collection<EntityPrestamos> listaPrestamos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityUsuario that = (EntityUsuario) o;
        return id == that.id && Objects.equals(nombre, that.nombre) && Objects.equals(apellidos, that.apellidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidos);
    }

    public Collection<EntityPrestamos> getListaPrestamos() {
        return listaPrestamos;
    }

    public void setListaPrestamos(Collection<EntityPrestamos> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }
}
