package edu.registraduriaMintic2022.msseguridad.repositorios;


import org.springframework.data.mongodb.repository.MongoRepository;

import edu.registraduriaMintic2022.msseguridad.modelos.Rol;


public interface RepoRol extends MongoRepository <Rol, String> {

    
}
