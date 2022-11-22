package edu.registraduriaMintic2022.msseguridad.modelos;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document
public class PermisoRol {
    @Id
    @Setter
    private String _id;

    @DBRef
    private Rol rol;

    @DBRef
    private Permiso permiso;

    public PermisoRol() {
    }

    public void setPermission(Permiso permiso) {
        this.permiso = permiso;
    }

}
