package com.course.practicaljava2.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.practicaljava2.repository.CarElasticRepository;
import com.course.practicaljava2.rest.domain.Car;
import com.course.practicaljava2.rest.service.CarService;

@RestController
@RequestMapping("/api/car/v1")
public class CarRestController {

	@Autowired
	private CarElasticRepository carElasticRepository;

	private Random random = new Random();

	private Logger log = LoggerFactory.getLogger(CarRestController.class);

	@Autowired
	private CarService carService;

	@GetMapping(path = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
	public Car random() {
		return carService.generateCar();
	}

	@PostMapping(path = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String echo(@RequestBody Car car) {
		log.info("The car is " + car);
		return car.toString();
	}

	@GetMapping(path = "/random-cars", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> randomCars() {
		var cars = new ArrayList<Car>();
		for (int i = 0; i < random.nextInt(6); i++) {
			cars.add(carService.generateCar());
		}
		return cars;
	}

	@GetMapping(path = "/cars/count")
	public long countCar() {
		return carElasticRepository.count();
	}

	@PostMapping(path = "/cars")
	public Car createCar(@RequestBody Car car) {

		return carElasticRepository.save(car);
	}

	@GetMapping("/cars/{id}")
	public Car findCarById(@PathVariable String id) {
		return carElasticRepository.findById(id).orElse(null);
	}

	@PutMapping("/cars/{id}")
	public Car updateCarById(@PathVariable String id, @RequestBody Car updateCar) {
		updateCar.setId(id);
		return carElasticRepository.save(updateCar);
	}

	@GetMapping("/cars/{brand}/{color}")
	public List<Car> findByPath(@PathVariable String brand, @PathVariable String color) {
		return carElasticRepository.findByBrandAndColor(brand, color);
	}

}
