package org.ieszaidinvergeles.dam.bibliotecaspringboot.models.repositories;

import org.ieszaidinvergeles.dam.bibliotecaspringboot.models.entities.EntityLibro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryLibro extends CrudRepository<EntityLibro, Integer> {



}
