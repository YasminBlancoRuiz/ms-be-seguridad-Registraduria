package edu.registraduriaMintic2022.msseguridad.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import edu.registraduriaMintic2022.msseguridad.modelos.Permiso;

public interface RepoPermiso extends MongoRepository<Permiso, String>{
    @Query("{'url':?0,'metodo':?1}")
    Permiso getPermiso(String url, String metodo);
}
