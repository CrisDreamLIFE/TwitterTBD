package cl.usach.gobierno.controllers;

import cl.usach.gobierno.entities.Usuario;
import cl.usach.gobierno.entities.Political;
import cl.usach.gobierno.repositories.UsuarioRepository;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.usach.gobierno.MongoConnection;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

import java.util.HashMap;
import java.util.Map;


@RestController  
@RequestMapping("/usuarios")
public class usuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Usuario> getAllUsers() {
		return usuarioRepository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public  Usuario findOne(@PathVariable("id") Integer id) {
		return usuarioRepository.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Usuario create(@RequestBody Usuario resource) {
	     return usuarioRepository.save(resource);
	}
	 
}