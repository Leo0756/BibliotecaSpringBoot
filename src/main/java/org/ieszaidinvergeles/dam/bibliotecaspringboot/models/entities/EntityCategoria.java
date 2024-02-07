package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "categoria", schema = "BIBLIOTECA")
public class EntityCategoria {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "categoria", nullable = true, length = 30)
    @NotBlank(message = "El nombre de la categoria no puede estar vacio")
    @Size(max = 30, message = "El nombre de la categoria no puede tener mas de 30 caracteres")
    private String categoria;
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private Collection<EntityLibro> listaLibros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCategoria that = (EntityCategoria) o;
        return id == that.id && Objects.equals(categoria, that.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoria);
    }

    public Collection<EntityLibro> getListaLibros() {
        return listaLibros;
    }

    public void setListaLibros(Collection<EntityLibro> listaLibros) {
        this.listaLibros = listaLibros;
    }
}
