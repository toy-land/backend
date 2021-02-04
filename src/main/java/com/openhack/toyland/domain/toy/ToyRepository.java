package com.openhack.toyland.domain.toy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ToyRepository extends JpaRepository<Toy, Long> {

	@Query(
		value = "SELECT * FROM toy ORDER BY id",
		countQuery = "SELECT count(*) FROM toy",
		nativeQuery = true)
	Page<Toy> findAllToysWithPagination(Pageable pageable);
}
