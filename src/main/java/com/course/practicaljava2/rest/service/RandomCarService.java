package com.course.practicaljava2.rest.service;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.course.practicaljava2.rest.domain.Car;
import com.course.practicaljava2.rest.domain.Engine;
import com.course.practicaljava2.rest.domain.Tire;
import com.course.practicaljava2.util.RandomDateUtil;

@Service
public class RandomCarService implements CarService {

	private Random random = new Random();

	@Override
	public Car generateCar() {
		var randomBrand = BRANDS.get(random.nextInt(BRANDS.size()));
		var randomColor = COLORS.get(random.nextInt(COLORS.size()));
		var randomType = TYPES.get(random.nextInt(TYPES.size()));
		var randomCount = random.nextInt(ADDITIONAL_FEATURES.size());

		var car = new Car(randomBrand, randomColor, randomType);

		car.setAvailable(random.nextBoolean());
		car.setPrice(5000 + random.nextInt(7001));

		var additionalFeatures = new ArrayList<String>();
		for (int i = 1; i <= randomCount; i++) {
			additionalFeatures.add(ADDITIONAL_FEATURES.get(i - 1));
		}
		car.setFirstReleaseDate(RandomDateUtil.generateRandomDate());
		car.setAdditionalFeatures(additionalFeatures);

		var randomFuelType = FUEL_TYPES.get(random.nextInt(FUEL_TYPES.size()));
		var randomHorsePower = 100 + random.nextInt(121);
		var randomEngine = new Engine(randomFuelType, randomHorsePower);
		car.setEngine(randomEngine);

		var randomCompatibleTires = new ArrayList<Tire>();
		for (int i = 0; i < 3; i++) {
			var randomTireManufacturer = TIRE_MANUFACTURERS.get(random.nextInt(TIRE_MANUFACTURERS.size()));
			var tireSize = 15 + random.nextInt(3);
			var tirePrice = 200 + random.nextInt(201);
			var randomTire = new Tire(randomTireManufacturer, tireSize, tirePrice);
			randomCompatibleTires.add(randomTire);
		}
		car.setCompatibleTires(randomCompatibleTires);
		var secretFeature = random.nextBoolean() != false ? "can fly" : null;
		car.setSecretFeature(secretFeature);

		return car;
	}

}
