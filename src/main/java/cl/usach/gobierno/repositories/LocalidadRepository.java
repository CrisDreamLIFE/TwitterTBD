package cl.usach.gobierno.repositories;

import cl.usach.gobierno.entities.Localidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LocalidadRepository extends CrudRepository<Localidad, Long>{
    @Query(value = "SELECT * FROM localidad l  where l.idregion = :id", nativeQuery = true)
    Localidad findById(@Param("id") int id);
}