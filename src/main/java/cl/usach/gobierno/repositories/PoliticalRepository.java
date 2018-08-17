package cl.usach.gobierno.repositories;

import cl.usach.gobierno.entities.Political;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PoliticalRepository extends CrudRepository<Political, Long>{
    @Query(value = "SELECT * FROM political u  where u.id = :id", nativeQuery = true)
    Political findById(@Param("id") int id);

    @Query(value = "SELECT * FROM political u  where u.nombre = :nombre", nativeQuery = true)
    Political findByName(String nombre);
}
