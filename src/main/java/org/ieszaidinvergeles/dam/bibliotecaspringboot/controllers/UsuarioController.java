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

    @GetMapping("/select/all")
    public List<EntityUsuario> buscarSedes() {
        return (List<EntityUsuario>) usuarioRepository.findAll();
    }

    @GetMapping("/select/{id}")
    public ResponseEntity<EntityUsuario> buscarUsuarioPorId(@PathVariable(value = "id") int id) {
        Optional<EntityUsuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok().body(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insert/")
    public EntityUsuario insertarUsuario(@Valid @RequestBody EntityUsuario usuario) {
        return usuarioRepository.save(usuario);
    }

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody EntityUsuario nuevoUsuario, @PathVariable(value = "id") int id) {
        Optional<EntityUsuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuario.get().setNombre(nuevoUsuario.getNombre());
            usuario.get().setApellidos(nuevoUsuario.getApellidos());
            usuarioRepository.save(usuario.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
