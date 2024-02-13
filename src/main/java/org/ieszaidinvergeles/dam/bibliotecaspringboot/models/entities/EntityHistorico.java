package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "historico", schema = "BIBLIOTECA")
public class EntityHistorico {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idhistorico", nullable = false)
    private int idHistorico;
    @Basic
    @NotEmpty(message = "El nombre del usuario no puede estar vacío.")
    @Size(min = 2, max = 40, message = "El nombre tiene que tener entre 2 y 40 caracteres.")
    @Column(name = "user", nullable = false, length = 40)
    private String user;
    @Basic
    @NotNull(message = "La fecha no puede esta vacía.")
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
    @Basic
    @NotEmpty(message = "La información adicional no puede estar vacía.")
    @Column(name = "info", length = 40)
    private String info;

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityHistorico that = (EntityHistorico) o;
        return idHistorico == that.idHistorico && Objects.equals(user, that.user) && Objects.equals(fecha, that.fecha) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHistorico, user, fecha, info);
    }
}
