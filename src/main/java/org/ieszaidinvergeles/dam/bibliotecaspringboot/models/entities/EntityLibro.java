package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "libro", schema = "BIBLIOTECA")
public class EntityLibro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = true, length = 50)
    @NotBlank(message="El nombre no puede estar vacío")
    @Size(min = 1, max = 50, message = "El nombre tiene que tener entre 1 y 50 caracteres")
    private String nombre;
    @Basic
    @Column(name = "autor", nullable = true, length = 50)
    @NotBlank(message="El autor no puede estar vacío")
    @Size(min = 1, max = 50, message = "El autor tiene que tener entre 1 y 50 caracteres")
    private String autor;
    @Basic
    @Column(name = "editorial", nullable = true, length = 50)
    @NotBlank(message="El editorial no puede estar vacío")
    @Size(min = 1, max = 50, message = "El editorial tiene que tener entre 1 y 50 caracteres")
    private String editorial;
    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    @NotNull(message="La categoria no puede estar vacía")
    private EntityCategoria categoria;

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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLibro that = (EntityLibro) o;
        return id == that.id && Objects.equals(nombre, that.nombre) && Objects.equals(autor, that.autor) && Objects.equals(editorial, that.editorial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, autor, editorial);
    }

    public EntityCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(EntityCategoria categoria) {
        this.categoria = categoria;
    }

}
