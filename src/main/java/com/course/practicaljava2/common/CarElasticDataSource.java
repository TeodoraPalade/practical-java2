
package com.course.practicaljava2.common;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.course.practicaljava2.repository.CarElasticRepository;
import com.course.practicaljava2.rest.domain.Car;
import com.course.practicaljava2.rest.service.CarService;

@Component
public class CarElasticDataSource {

	@Autowired
	private CarElasticRepository carElasticRepository;

	@Autowired
	private CarService carService;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger log = LoggerFactory.getLogger(CarElasticDataSource.class);

	@EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
	public void populateData() {
		log.info("Start DELETE");
		var response = restTemplate.exchange("http://localhost:9200/practical-java2", HttpMethod.DELETE, null,
				String.class);
		log.info("DELETE response: " + response.getBody());
		var cars = new ArrayList<Car>();
		for (int i = 0; i < 10000; i++) {
			cars.add(carService.generateCar());
		}
		carElasticRepository.saveAll(cars);
		log.info("Car in Elasticsearch : " + carElasticRepository.count());
	}

}
