package cl.citiaps.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.citiaps.spring.backend.entities.Political;

public interface PoliticalRepository extends PagingAndSortingRepository<Political, Integer> {
	

}
