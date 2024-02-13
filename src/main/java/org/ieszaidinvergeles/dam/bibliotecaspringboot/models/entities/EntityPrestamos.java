package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "prestamos", schema = "BIBLIOTECA")
public class EntityPrestamos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idprestamo", nullable = false)
    private int idPrestamo;
    @Basic
    @Column(name = "fechaprestamo", nullable = true)
    private LocalDate fechaPrestamo;
    @ManyToOne
    @JoinColumn(name = "idlibro", referencedColumnName = "id")
    private EntityLibro libro;
    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "id")
    private EntityUsuario usuario;

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
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
