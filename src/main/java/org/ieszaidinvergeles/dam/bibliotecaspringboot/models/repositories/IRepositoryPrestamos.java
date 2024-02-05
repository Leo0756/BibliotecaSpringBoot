package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories;

import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityPrestamos;
import org.springframework.data.repository.CrudRepository;

public interface IRepositoryPrestamos extends CrudRepository<EntityPrestamos, Integer> {
}
