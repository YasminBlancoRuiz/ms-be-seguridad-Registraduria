package edu.registraduriaMintic2022.msseguridad.controladores;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.registraduriaMintic2022.msseguridad.modelos.Rol;
import edu.registraduriaMintic2022.msseguridad.repositorios.RepoRol;
import org.springframework.http.HttpStatus;

@CrossOrigin
@RestController
@RequestMapping("/roles")
public class ControladorRol {
    @Autowired
    private RepoRol repositorioRol;

    @GetMapping("")
    public List<Rol> index(){
        return this.repositorioRol.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol create(@RequestBody  Rol infoRol){
        return this.repositorioRol.save(infoRol);
    }
    @GetMapping("{id}")
    public Rol show(@PathVariable String id){
        Rol rolActual=this.repositorioRol
                        .findById(id)
                        .orElse(null);
        return rolActual;
    }
    /**
     * @param id
     * @param infoRol
     * @return
     */
    @PutMapping("{id}")
    public Rol update(@PathVariable String id,@RequestBody  Rol infoRol){
        Rol rolActual=this.repositorioRol
                .findById(id)
                .orElse(null);
        if (rolActual!=null){
            rolActual.setDescripcion(infoRol.getDescripcion());
            rolActual.setTipo(infoRol.getTipo());
            return this.repositorioRol.save(rolActual);
        }else{
            return  null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Rol rolActual=this.repositorioRol
                .findById(id)
                .orElse(null);
        if (rolActual!=null){
            this.repositorioRol.delete(rolActual);
        }
    }
    
}
