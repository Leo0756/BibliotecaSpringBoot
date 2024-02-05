package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "prestamos", schema = "BIBLIOTECA", catalog = "")
public class EntityPrestamos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPrestamo", nullable = false)
    private int idPrestamo;
    @Basic
    @Column(name = "fechaPrestamo", nullable = true)
    private Timestamp fechaPrestamo;
    @ManyToOne
    @JoinColumn(name = "idLibro", referencedColumnName = "id")
    private EntityLibro libro;
    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private EntityUsuario usuario;

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Timestamp getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Timestamp fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPrestamos that = (EntityPrestamos) o;
        return idPrestamo == that.idPrestamo && Objects.equals(fechaPrestamo, that.fechaPrestamo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrestamo, fechaPrestamo);
    }

    public EntityLibro getLibro() {
        return libro;
    }

    public void setLibro(EntityLibro libro) {
        this.libro = libro;
    }

    public EntityUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(EntityUsuario usuario) {
        this.usuario = usuario;
    }
}
