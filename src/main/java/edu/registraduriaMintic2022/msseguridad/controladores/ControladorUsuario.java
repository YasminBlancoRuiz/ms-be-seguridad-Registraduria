package edu.registraduriaMintic2022.msseguridad.controladores;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
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
import org.springframework.web.server.ResponseStatusException;

import com.mongodb.event.ConnectionCheckOutFailedEvent.Reason;

import lombok.extern.apachecommons.CommonsLog;
import edu.registraduriaMintic2022.msseguridad.modelos.Rol;
import edu.registraduriaMintic2022.msseguridad.modelos.Usuario;
import edu.registraduriaMintic2022.msseguridad.repositorios.RepoRol;
import edu.registraduriaMintic2022.msseguridad.repositorios.RepoUsuario;


@CommonsLog
@RestController
@CrossOrigin
@RequestMapping("/usuarios")

public class ControladorUsuario {
    @Autowired private RepoUsuario repositorio;
    @Autowired private RepoRol repositorioRol;
    private String message;

    //Todos los metodos estan asociados a una petición

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<Usuario> index(){  //asociada a una petición de tipo get, un listado de usuarios me devuelve
        log.debug(message="[GET /usuarios]");
        List<Usuario> l = null;
        try{
            l = repositorio.findAll();
        }
        catch (Exception e){
            log.error("[GET /usuarios]" + e.getMessage() + " -> " + e.getCause());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage(), e.getCause());
            
        }
        return l;
    }

    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public Usuario retrieve (@PathVariable String id){  //asociada a una petición de tipo get
        log.debug(message="[GET /usuarios/" + id +"]");
        Usuario u = repositorio.findById(id).orElse(null);
        if(u == null){
            log.error("[GET /usuarios/" + id +"] El usuario no pudo ser intanciado");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no fue encontrado");
        }
        return u;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create (@RequestBody Usuario infoUsuario){ //asociada a una petición de tipo Post
        log.debug(message="[POST /usuarios]");
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena())); //encripta el password en la base de datos
        Usuario u = null;
        try {
           u = repositorio.save(infoUsuario);
            
        } catch (Exception e) {
            log.error("[POST /usuarios]" + e.getMessage() + " -> " + e.getCause());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage(), e.getCause());    
        }
        return u;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario){ //asociada a una petición de tipo Put
        log.debug("[PUT /usuarios "+id+"] " + infoUsuario);
        Usuario usuarioActual=repositorio.findById(id).orElse(null);
        if(usuarioActual!= null){
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioActual.setEmail(infoUsuario.getEmail());
            usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            return repositorio.save(usuarioActual);
        }
        else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        log.debug(message="[DELETE /usuarios "+id+"]");
        Usuario usuarioActual=this.repositorio.findById(id).orElse(null);
        if (usuarioActual!=null){
            this.repositorio.delete(usuarioActual);
        }
    }

  

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}/rol/{id_rol}")

    public Usuario asignarRol(@PathVariable String id, @PathVariable String id_rol){
        log.debug("[ASIGNARrol /usuarios "+id+"] -> rol "+ id_rol);
        Usuario u = repositorio.findById(id).orElse(null);
        Rol     r = repositorioRol.findById(id_rol).orElse(null);

        if(u == null || r == null){
            log.debug("[ASIGNARrol /usuarios "+id+"] -> rol "+ id_rol);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "El usuario o el Rol no existe");
        }
        u.setRol(r);
        return repositorio.save(u);

    }
    
    @PostMapping("/validate")
    public Usuario validate(@RequestBody Usuario infoUsuario,
                            final HttpServletResponse response) throws IOException {
        Usuario usuarioActual=this.repositorio
                .getUserByEmail(infoUsuario.getEmail());
        if (usuarioActual!=null &&
            usuarioActual.getContrasena().equals(convertirSHA256(infoUsuario.getContrasena()))) {
            usuarioActual.setContrasena("");
            return usuarioActual;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
             return null;
        }
    }


    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
}
