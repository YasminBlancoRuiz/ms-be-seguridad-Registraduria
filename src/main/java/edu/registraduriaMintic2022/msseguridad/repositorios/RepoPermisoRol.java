package edu.registraduriaMintic2022.msseguridad.repositorios;

import edu.registraduriaMintic2022.msseguridad.modelos.PermisoRol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepoPermisoRol extends MongoRepository<PermisoRol, String>{
    @Query("{'rol.$id': ObjectId(?0),'permiso.$id': ObjectId(?1)}")
    

    PermisoRol getPermisoRol(String id_rol, String id_permiso);
}
