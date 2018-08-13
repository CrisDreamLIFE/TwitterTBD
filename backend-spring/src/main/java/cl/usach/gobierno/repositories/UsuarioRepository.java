package cl.usach.gobierno.repositories;

import cl.usach.gobierno.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    @Query(value = "SELECT * FROM user u  where u.id = :id", nativeQuery = true)
    Usuario findById(@Param("id") int id);

}

