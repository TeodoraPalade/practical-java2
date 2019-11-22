package com.course.practicaljava2.rest.service;

import java.util.List;

import com.course.practicaljava2.rest.domain.Car;

public interface CarService {
	List<String> BRANDS = List.of("Toyota", "Honda", "Ford", "Opel", "BMW");
	List<String> COLORS = List.of("Red", "Black", "White", "Blue", "Green");
	List<String> TYPES = List.of("Sedan", "SUV", "MPV", "Truck", "Coupe");
	List<String> ADDITIONAL_FEATURES = List.of("GPS", "Alarm", "Sunroof", "Media player", "Leather seats");
	List<String> FUEL_TYPES = List.of("Petrol", "Electric", "Hybrid");
	List<String> TIRE_MANUFACTURERS = List.of("Goodyear", "Bridgestone", "Dunlop");

	public Car generateCar();
}
