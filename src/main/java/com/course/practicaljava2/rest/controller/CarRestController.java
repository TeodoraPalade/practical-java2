package com.course.practicaljava2.rest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.practicaljava2.exception.IllegalApiParamException;
import com.course.practicaljava2.repository.CarElasticRepository;
import com.course.practicaljava2.rest.domain.Car;
import com.course.practicaljava2.rest.domain.ErrorResponse;
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
	public ResponseEntity<Object> findByPath(@PathVariable String brand, @PathVariable String color,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.SERVER, "SPRING");
		headers.add("Custom", "Custom response header");

		if (StringUtils.isNumeric(color)) {
			var errorResponse = new ErrorResponse("Invalid color", System.currentTimeMillis());
			return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
		}

		var pageable = PageRequest.of(page, size);
		var cars = carElasticRepository.findByBrandAndColor(brand, color, pageable).getContent();
		return ResponseEntity.ok().headers(headers).body(cars);
	}

	@GetMapping("/cars")
	public List<Car> findCarsByParam(@RequestParam String brand, @RequestParam String color,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		var pageable = PageRequest.of(page, size);

		if (StringUtils.isNumeric(color)) {
			throw new IllegalArgumentException("Invalid color:" + color);
		}
		if (StringUtils.isNumeric(brand)) {
			throw new IllegalApiParamException("Invalid brand:" + brand);
		}

		return carElasticRepository.findByBrandAndColor(brand, color, pageable).getContent();
	}

	@GetMapping("/cars/date")
	public List<Car> findCarsReleasedAfter(
			@RequestParam(name = "first_release_date") @DateTimeFormat(pattern = "yyy-MM-dd") Date firstReleaseDate) {
		return carElasticRepository.findByFirstReleaseDateAfter(firstReleaseDate.getTime());

	}

	@GetMapping("/header-one")
	public String headerByAnnotation(@RequestHeader(name = "User-agent", required = false) String headerUserAgent,
			@RequestHeader(name = "Practical-java2", required = false) String headerPracticalJava) {
		var sb = new StringBuilder();
		sb.append("User-agent : " + headerUserAgent);
		sb.append("||");
		sb.append("Practical-java2 : " + headerPracticalJava);
		return sb.toString();
	}

	@GetMapping("/header-two")
	public String headerByServlet(HttpServletRequest request) {
		var sb = new StringBuilder();
		sb.append("User-agent : " + request.getHeader("User-agent"));
		sb.append("||");
		sb.append("Practical-java2 : " + request.getHeader("Practical-java2"));
		return sb.toString();
	}

	@GetMapping("/header-three")
	public Map<String, String> headerByAll(@RequestHeader HttpHeaders headers) {
		return headers.toSingleValueMap();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleInvalidColorException(IllegalArgumentException ex) {
		var errorMessage = "Exception " + ex.getMessage();
		log.warn(errorMessage);

		var errorResponse = new ErrorResponse(errorMessage, System.currentTimeMillis());
		return new ResponseEntity(errorResponse, null, HttpStatus.BAD_REQUEST);
	}

	// @RestControllerAdvice // on the class -the ex can be thrown globally, not
	// only in it's own controller
	@ExceptionHandler(IllegalApiParamException.class)
	public ResponseEntity<ErrorResponse> handleIllegalApiParamException(IllegalArgumentException ex) {
		var errorMessage = "Exception " + ex.getMessage();
		log.warn(errorMessage);

		var errorResponse = new ErrorResponse(errorMessage, System.currentTimeMillis());
		return new ResponseEntity(errorResponse, null, HttpStatus.BAD_REQUEST);
	}
}
