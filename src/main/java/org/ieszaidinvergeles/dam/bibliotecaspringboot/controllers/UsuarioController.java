package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import jakarta.validation.Valid;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers.helper.HistoricoHelper;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityUsuario;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryHistorico;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los usuarios.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/biblioteca/usuarios")
public class UsuarioController {

    @Autowired
    private IRepositoryUsuario usuarioRepository;

    @Autowired
    private IRepositoryHistorico historicoRepository;

    /**
     * Selecciona y devuelve todos los usuarios almacenados en la tabla Usuario.
     *
     * @return Un objeto de tipo List<EntityUsuario>.
     */
    @GetMapping
    public List<EntityUsuario> buscarUsuarios() {
        HistoricoHelper.guardarSentencia("ip", historicoRepository, "SELECT * FROM usuario;");
        return (List<EntityUsuario>) usuarioRepository.findAll();
    }

    /**
     * Selecciona y devuelve un usuario en concreto a través de un identificador numérico.
     *
     * @param id El identificador del usuario a seleccionar.
     * @return Un objeto ResponseEntity<EntityUsuario>.
     */
    @GetMapping("/select/{id}")
    public ResponseEntity<EntityUsuario> buscarUsuarioPorId(@PathVariable(value = "id") int id) {
        Optional<EntityUsuario> usuario = usuarioRepository.findById(id);

        HistoricoHelper.guardarSentencia("IP", historicoRepository, "SELECT * FROM usuario WHERE id = " + id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok().body(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Inserta un usuario en la tabla Usuario a través de un objeto EntityUsuario.
     *
     * @param usuario Un objeto de tipo EntityUsuario que es introducido a través de un formato JSON.
     * @return Un objeto de tipo EntityUsuario.
     */

    @PostMapping
    public EntityUsuario insertarUsuario(@Valid @RequestBody EntityUsuario usuario) {
        usuario.setId(0);
        HistoricoHelper.guardarSentencia("IP", historicoRepository, "INSERT INTO usuario (nombre, apellidos) " + "VALUES (" + usuario.getNombre() + "," + usuario.getApellidos() + ")");
        return usuarioRepository.save(usuario);
    }

    /**
     * Borra un usuario de la tabla Usuario a través de un identificador.
     *
     * @param id El identificador del usuario a borrar.
     * @return Un objeto de tipo ResponseEntity<?>.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable(value = "id") int id) {
        Optional<EntityUsuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            HistoricoHelper.guardarSentencia("IP", historicoRepository, "DELETE FROM usuario WHERE id=" + id);
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza un usuario a través de un objeto de tipo EntityUsuario y un identificador.
     *
     * @param nuevoUsuario El objeto de tipo EntityUsuario con los datos a actualizar.
     * @param id           El identificador del usuario a modificar.
     * @return Un objeto de tipo ResponseEntity<?>.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody EntityUsuario nuevoUsuario, @PathVariable(value = "id") int id) {
        Optional<EntityUsuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            HistoricoHelper.guardarSentencia("IP", historicoRepository, "UPDATE usuario SET " + "nombre =" + nuevoUsuario.getNombre() + "," + "apellidos =" + nuevoUsuario.getApellidos() + "WHERE id=" + id);
            usuario.get().setNombre(nuevoUsuario.getNombre());
            usuario.get().setApellidos(nuevoUsuario.getApellidos());
            usuario.get().setListaPrestamos(nuevoUsuario.getListaPrestamos());
            usuarioRepository.save(usuario.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
