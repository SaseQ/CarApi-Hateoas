package it.marczuk.pracadomowa_tydzien3.service;

import it.marczuk.pracadomowa_tydzien3.model.Car;
import it.marczuk.pracadomowa_tydzien3.model.Color;
import it.marczuk.pracadomowa_tydzien3.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepo repository;

    @Autowired
    public CarServiceImpl(CarRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<Car> getAllCars() {
        return repository.findAll();
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        List<Car> carList = repository.findAll();
        return carList.stream().filter(car -> car.getId() == id).findFirst();
    }

    @Override
    public List<Car> getCarsByColor(String color) {
        List<Car> carList = repository.findAll();
        return carList.stream().filter(car -> car.getColor().toString().equals(color)).collect(Collectors.toList());
    }

    @Override
    public Optional<Car> addCar(Car car) {
        List<Car> carList = repository.findAll();
        boolean isCarExists = carList.stream().anyMatch(newCar -> newCar.getMark().equals(car.getMark()));
        return isCarExists ? Optional.empty() : Optional.of(saveCar(car));
    }

    @Override
    public Car modCar(Car newCar) {
        Long id = newCar.getId()-1;
        repository.deleteById(id);
        repository.save(newCar);
        return newCar;
    }

    @Override
    public Optional<Car> modCarParameter(Long id, String modify) {
        String[] mod = modify.split("\"");
        String type = mod[1];
        String modArg = mod[3];
        return updateCarMethod(id, type, modArg);
    }

    @Override
    public Optional<Car> removeCar(Long id) {
        List<Car> carList = repository.findAll();
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        first.ifPresent(repository::delete);
        return first;
    }

    private Optional<Car> updateCarMethod(long id, String type, String modArg){
        List<Car> carList = repository.findAll();
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if(first.isPresent()) {
            if(type.equals("mark")){
                first.get().setMark(modArg);
            }
            if(type.equals("model")){
                first.get().setModel(modArg);
            }
            if(type.equals("color")){
                first.get().setColor(Color.valueOf(modArg));
            }
            return first;
        }
        return Optional.empty();
    }

    private Car saveCar(Car car) {
        repository.save(car);
        return car;
    }
}
