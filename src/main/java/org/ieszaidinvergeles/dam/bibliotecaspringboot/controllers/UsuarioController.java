package org.ieszaidinvergeles.dam.bibliotecaspringboot.controllers;

import jakarta.validation.Valid;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityUsuario;
import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories.IRepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca/usuarios")
public class UsuarioController {

    @Autowired
    private IRepositoryUsuario usuarioRepository;

    /**
     * Selecciona y devuelve todos los usuarios almacenados en la tabla Usuario.
     *
     * @return Un objeto de tipo List<EntityUsuario>.
     */
    @GetMapping("/select/all")
    public List<EntityUsuario> buscarUsuarios() {
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
    @PostMapping("/insert/")
    public EntityUsuario insertarUsuario(@Valid @RequestBody EntityUsuario usuario) {
        usuario.setId(0);
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
