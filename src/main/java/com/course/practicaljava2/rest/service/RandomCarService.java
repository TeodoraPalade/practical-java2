package com.course.practicaljava2.rest.service;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.course.practicaljava2.rest.domain.Car;
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
		var additionalFeatures = new ArrayList<String>();
		for (int i = 1; i <= randomCount; i++) {
			additionalFeatures.add(ADDITIONAL_FEATURES.get(i - 1));
		}
		var car = new Car(randomBrand, randomColor, randomType);
		car.setAvailable(random.nextBoolean());
		car.setPrice(5000 + random.nextInt(7001));
		car.setFirstReleaseDate(RandomDateUtil.generateRandomDate());
		car.setAdditionalFeatures(additionalFeatures);
		return car;
	}

}
