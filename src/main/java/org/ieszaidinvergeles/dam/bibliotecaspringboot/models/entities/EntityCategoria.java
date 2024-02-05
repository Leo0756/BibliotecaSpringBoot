package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "categoria", schema = "BIBLIOTECA", catalog = "")
public class EntityCategoria {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "categoria", nullable = true, length = -1)
    private String categoria;
    @OneToMany(mappedBy = "categoria")
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
