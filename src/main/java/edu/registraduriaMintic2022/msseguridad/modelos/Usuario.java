package edu.registraduriaMintic2022.msseguridad.modelos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;


@Document()
@Data
public class Usuario {
    @Id
    @Setter(AccessLevel.NONE) // No tiene acceso 
    private String _id;

    private String seudonimo; // puede ser nulo
    private String email;
    private String contrasena;
    @DBRef private Rol rol; // Clave foranea a Rol

    @JsonIgnore
    public String getContrasena(){  //Me devuelve la contraseña
        return contrasena;
    }
    @JsonProperty
    public void setContrasena(String contrasena){  // me reconozca el set y no el get
    if(contrasena != null) this.contrasena = contrasena;
    }
}