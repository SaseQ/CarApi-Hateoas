package it.marczuk.pracadomowa_tydzien3.service;

import it.marczuk.pracadomowa_tydzien3.model.Car;
import it.marczuk.pracadomowa_tydzien3.model.Color;
import it.marczuk.pracadomowa_tydzien3.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return repository.findAll().stream().filter(car -> car.getId() == id).findFirst();
    }

    @Override
    public List<Car> getCarsByColor(String color) {
        return repository.findAll().stream().filter(car -> car.getColor().toString().equals(color)).collect(Collectors.toList());
    }

    @Override
    public Optional<Car> addCar(Car car) {
        boolean isCarExists = repository.findAll().stream().anyMatch(newCar -> newCar.getMark().equals(car.getMark()));
        return isCarExists ? Optional.empty() : Optional.of(saveCar(car));
    }

    @Override
    @Transactional
    public Car modCar(Car newCar) {
        repository.deleteById(newCar.getId());
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
        Optional<Car> first = repository.findAll().stream().filter(car -> car.getId() == id).findFirst();
        first.ifPresent(repository::delete);
        return first;
    }

    private Optional<Car> updateCarMethod(long id, String type, String modArg){
        Optional<Car> first = repository.findAll().stream().filter(car -> car.getId() == id).findFirst();
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
            repository.deleteById(first.get().getId());
            repository.save(first.get());
            return first;
        }
        return Optional.empty();
    }

    private Car saveCar(Car car) {
        repository.save(car);
        return car;
    }
}
