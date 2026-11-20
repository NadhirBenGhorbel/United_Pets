package api;

import java.time.LocalDate;

public record AnimalRequest(
        String name,
        String breed,
        String status,
        String type,
        int age,
        LocalDate enrollmentDate,
        String image,
        String description
) {
    public void validate() {
        if (name == null || name.isBlank()
                || breed == null || breed.isBlank()
                || status == null || status.isBlank()
                || type == null || type.isBlank()
                || age < 0
                || enrollmentDate == null) {
            throw new IllegalArgumentException(
                    "name, breed, status, type and enrollmentDate are required; age must be zero or greater");
        }
    }
}
