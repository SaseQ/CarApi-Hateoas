package it.marczuk.pracadomowa_tydzien3.controller;

import it.marczuk.pracadomowa_tydzien3.entity.Car;
import it.marczuk.pracadomowa_tydzien3.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getSneakers(Model model) {
        List<Car> carList = carService.getAllCars();
        model.addAttribute("cars", carList);
        model.addAttribute("newCar", new Car());
        return "index";
    }

    @GetMapping("/add")
    public String addCarFormSite(Model model) {
        model.addAttribute("newCar", new Car());
        return "add";
    }

    @PostMapping("/addcar")
    public String addCar(@ModelAttribute Car car) {
        carService.addCar(car);
        return "redirect:/cars";
    }

    @PostMapping("/edit/{id}")
    public String modCar(@ModelAttribute Car car, @PathVariable Long id) {
        car.setId(id);
        carService.modCar(car);
        return "redirect:/cars";
    }

    @GetMapping("/remove/{id}")
    public String removeCar(@PathVariable Long id) {
        carService.removeCar(id);
        return "redirect:/cars";
    }
}
