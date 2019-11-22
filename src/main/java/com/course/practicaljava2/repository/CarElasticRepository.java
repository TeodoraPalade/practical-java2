
package com.course.practicaljava2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.course.practicaljava2.rest.domain.Car;

@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {

	public Page<Car> findByBrandAndColor(String brand, String color, Pageable pageable);

	@Query("{ \"range\":{ \"first_release_date\":{ \"gt\":?0 } } }")
	public List<Car> findByFirstReleaseDateAfter(long date);

}
