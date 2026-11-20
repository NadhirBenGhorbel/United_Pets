package api;

import entities.Animal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.ServiceAnimal;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalApiController {
    private static final Logger logger = LoggerFactory.getLogger(AnimalApiController.class);
    private final ServiceAnimal animalService;

    public AnimalApiController() {
        this.animalService = new ServiceAnimal();
    }

    @GetMapping
    public List<AnimalResponse> getAnimals() throws SQLException {
        requireDatabaseConnection();
        return animalService.Show().stream()
                .map(AnimalResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public AnimalResponse getAnimal(@PathVariable int id) throws SQLException {
        requireDatabaseConnection();
        return findAnimal(id);
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> createAnimal(@RequestBody AnimalRequest request) throws SQLException {
        requireDatabaseConnection();
        if (request == null) {
            throw new IllegalArgumentException("A JSON request body is required.");
        }
        request.validate();
        Animal animal = toEntity(request);
        animalService.add(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(AnimalResponse.from(animal));
    }

    @PutMapping("/{id}")
    public AnimalResponse updateAnimal(@PathVariable int id, @RequestBody AnimalRequest request) throws SQLException {
        requireDatabaseConnection();
        if (request == null) {
            throw new IllegalArgumentException("A JSON request body is required.");
        }
        request.validate();
        findAnimal(id);
        Animal animal = toEntity(request);
        animal.setAnimalId(id);
        animalService.update(animal);
        return AnimalResponse.from(animal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable int id) throws SQLException {
        requireDatabaseConnection();
        Animal existing = findAnimal(id);
        animalService.delete(existing);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ApiError(exception.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiError> handleDatabaseError(SQLException exception) {
        logger.error("REST API database operation failed", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Database operation failed."));
    }

    private void requireDatabaseConnection() throws SQLException {
        if (animalService.con == null || animalService.con.isClosed()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Database is unavailable.");
        }
    }

    private Animal findAnimal(int id) throws SQLException {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal ID must be positive.");
        }
        return animalService.Show().stream()
                .filter(animal -> animal.getAnimalId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal not found."));
    }

    private Animal toEntity(AnimalRequest request) {
        return new Animal(
                0,
                request.name(),
                request.breed(),
                request.status(),
                request.type(),
                request.age(),
                java.sql.Date.valueOf(request.enrollmentDate()),
                request.image(),
                request.description()
        );
    }

    public record ApiError(String message) {
    }
}
