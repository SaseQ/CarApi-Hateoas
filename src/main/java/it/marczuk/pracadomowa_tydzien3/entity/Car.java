package it.marczuk.pracadomowa_tydzien3.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car extends RepresentationModel<Car> {

    private Long id;
    private String mark;
    private String model;
    private Color color;

}
