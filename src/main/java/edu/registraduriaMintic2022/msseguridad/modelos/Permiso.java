package edu.registraduriaMintic2022.msseguridad.modelos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Document
@Data
public class Permiso {
    @Id
    @Setter(AccessLevel.NONE) //le establezco acces none para qeu no pued amodificarse el id
    private String _id;

    private String url;
    private String metodo;
    
}
