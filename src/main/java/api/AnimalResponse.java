package api;

import entities.Animal;

import java.time.LocalDate;

public record AnimalResponse(
        int id,
        String name,
        String breed,
        String status,
        String type,
        int age,
        LocalDate enrollmentDate,
        String image,
        String description
) {
    public static AnimalResponse from(Animal animal) {
        return new AnimalResponse(
                animal.getAnimalId(),
                animal.getAnimal_Name(),
                animal.getAnimal_Breed(),
                animal.getAnimal_Status(),
                animal.getAnimal_Type(),
                animal.getAge(),
                animal.getEnrollement_Date().toLocalDate(),
                animal.getAnimal_Image(),
                animal.getAnimal_description()
        );
    }
}
