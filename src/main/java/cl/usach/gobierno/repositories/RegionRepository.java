package cl.usach.gobierno.repositories;

import cl.usach.gobierno.entities.Region;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RegionRepository extends CrudRepository<Region, Long>{
    @Query(value = "SELECT * FROM region u  where u.idregion = :id", nativeQuery = true)
    Region findById(@Param("id") int id);
}