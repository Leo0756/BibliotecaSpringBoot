package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers.helper.HistoricoHelper;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityCategoria;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityLibro;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityPrestamos;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryHistorico;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryLibro;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryPrestamos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los prestamos.
 */
@RestController
@RequestMapping("/biblioteca/prestamos")
@CrossOrigin(origins = "*")
public class PrestamosController {
    @Autowired //inyección de dependencia: por tanto, será inicializado
    IRepositoryPrestamos prestamosRepository; //creando Spring automáticamente su instancia

    @Autowired
    IRepositoryHistorico historicoRepository;

    /**
     * Metodo de seleccion de todos los prestamos.
     *
     * @return Devuelve todos los prestamos existentes.
     */
    @GetMapping //endpoint para buscar todos
    // Tipo de solicitud HTTP --> GET
    public List<EntityPrestamos> buscarPrestamos() {
        // ... (código para buscar todos los libros)
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "SELECT * FROM prestamos");
        return (List<EntityPrestamos>) prestamosRepository.findAll();
    }

    /**
     * Metodo para buscar un prestamo por su ID, donde {id} es un parámetro de ruta.
     *
     * @param id parametro con el ID del prestamo
     * @return devuelve el prestamo o error dependiendo de si existe o no.
     */
    @GetMapping("/{id}") //endpoint para buscar un libro por id
    public ResponseEntity<EntityPrestamos> buscarLibroPorId(@PathVariable(value = "id") int id) {
        // ... (código para buscar un libro por su identificador)
        Optional<EntityPrestamos> prestamo = prestamosRepository.findById(id);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "SELECT * FROM prestamos WHERE id=" + id);

        if (prestamo.isPresent()) return ResponseEntity.ok().body(prestamo.get()); // HTTP 200 OK
        else return ResponseEntity.notFound().build(); // HTTP 404
    }

    /**
     * Metodo para insertar un prestamo.
     *
     * @param prestamo parametro que contiene el objeto EntityPrestamo a insertar
     * @return inserta el prestamo.
     */
    @PostMapping
    public EntityPrestamos guardarPrestamo(@Valid @RequestBody EntityPrestamos prestamo) {
        prestamo.setIdPrestamo(0);
        prestamo.setFechaPrestamo(LocalDate.now());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "INSERT INTO prestamos (idLibro, idUsuario, fechaPrestamo) VALUES (" + prestamo.getLibro().getId() + "," + prestamo.getUsuario().getId() + "," + prestamo.getFechaPrestamo() + ")");
        return prestamosRepository.save(prestamo); // Guarda el prestamo.
    }

    /**
     * Metodo para borrar un prestamo, donde {id} es un parámetro de ruta.
     *
     * @param id parametro con el prestamo a eliminar
     * @return devuelve el prestamo o error dependiendo de si existe o no.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarLibro(@PathVariable(value = "id") int id) {
        // ... (código para borrar un prestamo por su id)
        Optional<EntityPrestamos> prestamo = prestamosRepository.findById(id);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        if (prestamo.isPresent()) {
            HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "DELETE FROM prestamos WHERE id=" + id);
            prestamosRepository.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metodo para actualizar un prestamo existente, donde {id} es un parámetro de ruta.
     *
     * @param nuevoPrestamo objeto EntityPrestamos que contiene los nuevos datos del prestamo
     * @param id            parametro con el prestamo a actualizar
     * @return devuelve el prestamo o error dependiendo de si existe o no.
     */
    // Tipo de solicitud HTTP --> PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPrestamo(@Validated @RequestBody EntityPrestamos nuevoPrestamo, @PathVariable(value = "id") int id) {
        Optional<EntityPrestamos> prestamo = prestamosRepository.findById(id);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipCliente = request.getRemoteAddr();
        if (prestamo.isPresent()) {
            HistoricoHelper.guardarSentencia(ipCliente, historicoRepository, "UPDATE prestamos SET " + "idLibro=" + nuevoPrestamo.getLibro().getId() + "," + "idUsuario=" + nuevoPrestamo.getUsuario().getId() + "," + "fechaPrestamo" + nuevoPrestamo.getFechaPrestamo() + "WHERE id=" + id);

            prestamo.get().setFechaPrestamo(nuevoPrestamo.getFechaPrestamo());
            prestamo.get().setLibro(nuevoPrestamo.getLibro());
            prestamo.get().setUsuario(nuevoPrestamo.getUsuario());
            prestamosRepository.save(prestamo.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
