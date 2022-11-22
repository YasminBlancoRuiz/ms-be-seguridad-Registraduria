package edu.registraduriaMintic2022.msseguridad.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;


import edu.registraduriaMintic2022.msseguridad.modelos.Usuario;
public interface RepoUsuario extends MongoRepository <Usuario, String>{
    
    public Usuario getUserByEmail(String email);
}