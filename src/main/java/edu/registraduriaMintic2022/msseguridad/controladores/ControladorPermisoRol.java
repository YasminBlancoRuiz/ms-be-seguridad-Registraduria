package edu.registraduriaMintic2022.msseguridad.controladores;

import lombok.extern.apachecommons.CommonsLog;

import edu.registraduriaMintic2022.msseguridad.modelos.Rol;
import edu.registraduriaMintic2022.msseguridad.modelos.Permiso;
import edu.registraduriaMintic2022.msseguridad.modelos.PermisoRol;
import edu.registraduriaMintic2022.msseguridad.repositorios.RepoRol;
import edu.registraduriaMintic2022.msseguridad.repositorios.RepoPermiso;
import edu.registraduriaMintic2022.msseguridad.repositorios.RepoPermisoRol;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CommonsLog
@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisoRol {
    @Autowired
    private RepoRol repositorioRol;

    @Autowired
    private RepoPermiso repositorioPermiso;

    @Autowired
    private RepoPermisoRol repositorioPermisoRol;

    @GetMapping("")
    public List<PermisoRol> index(){
        log.debug("[GET /permisos-roles]");
        return this.repositorioPermisoRol.findAll();
    }

    @GetMapping("{id}")
    public PermisoRol show(@PathVariable String id){
        log.debug("[GET /permisos-roles" + id + "]");
        PermisoRol permisoRolActual = this.repositorioPermisoRol
                .findById(id)
                .orElse(null);
        return permisoRolActual;
    }
    @GetMapping("validarpermiso/rol/{id_rol}")
    public PermisoRol getPermiso(@PathVariable String id_rol, @RequestBody Permiso  infoPermiso){
        Permiso elPermiso=this.repositorioPermiso.getPermiso(infoPermiso.getUrl(),infoPermiso.getMetodo());
        Rol elRol=this.repositorioRol.findById(id_rol).get();
        if (elPermiso!=null && elRol!=null){
            return this.repositorioPermisoRol.getPermisoRol(elRol.get_id(),elPermiso.get_id());
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisoRol create(@PathVariable String id_rol, @PathVariable String id_permiso){
        log.debug("[POST /rol/" +id_rol + "/permiso/" + id_permiso + "]");
        PermisoRol nuevoPermisoRol = new PermisoRol();
        Rol rolActual = this.repositorioRol
                .findById(id_rol)
                .get();
        Permiso permisoActual = this.repositorioPermiso
                .findById(id_permiso)
                .get();
        if (nuevoPermisoRol != null && rolActual != null && permisoActual != null){
            nuevoPermisoRol.setPermiso(permisoActual);
            nuevoPermisoRol.setRol(rolActual);
            return repositorioPermisoRol.save(nuevoPermisoRol);
        }else{
            return null;
        }
    }

    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisoRol update(@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso){
        log.debug("[PUT /rol/" +id_rol + "/permiso/" + id_permiso + "]");
        PermisoRol permisoRolActual = this.repositorioPermisoRol.findById(id).orElse(null);
        Rol rolActual = this.repositorioRol
                .findById(id_rol)
                .get();
        Permiso permisoActual = this.repositorioPermiso
                .findById(id_permiso)
                .get();
        if (permisoRolActual != null && rolActual != null && permisoActual != null){
            permisoRolActual.setPermission(permisoActual);
            permisoRolActual.setRol(rolActual);
            return repositorioPermisoRol.save(permisoRolActual);
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        log.debug("DELETE /permisos-roles" + id + "]");
        PermisoRol thisPermissionRol = this.repositorioPermisoRol
                .findById(id)
                .orElse(null);
        if(thisPermissionRol != null){
            this.repositorioPermisoRol.delete(thisPermissionRol);
        }
    }

    
}
