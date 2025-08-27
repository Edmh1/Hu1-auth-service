package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.commons.AlreadyExistsException;
import co.com.pragma.model.user.commons.ValidationException;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Slf4j
public class UserUseCase {

    private final UserRepository userRepository;

    /**
     * Guarda un usuario validando los campos requeridos y la unicidad del email.
     */
    public Mono<User> saveUser(User user) {
        log.info("Intentando registrar usuario: {}", user.getEmail());

        // Validaciones básicas
        if (user.getName() == null || user.getName().isBlank()) {
            return Mono.error(new ValidationException("Nombre es obligatorio"));
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            return Mono.error(new ValidationException("Apellido es obligatorio"));
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Mono.error(new ValidationException("Email inválido"));
        }
        if (user.getBaseSalary() == null || user.getBaseSalary() < 0 || user.getBaseSalary() > 15000000) {
            return Mono.error(new ValidationException("Salario debe estar entre 0 y 15.000.000"));
        }

        // Chequeo de existencia de email y guardado
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("El correo {} ya está registrado", user.getEmail());
                        return Mono.error(new AlreadyExistsException("El correo ya está registrado"));
                    } else {
                        log.info("Guardando usuario: {}", user.getEmail());
                        return userRepository.save(user);
                    }
                });
    }

    public Mono<User> getUserById(Long id) {
        log.info("Consultando usuario por ID: {}", id);
        return userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        log.info("Consultando todos los usuarios");
        return userRepository.findAll();
    }

    public Mono<Void> deleteUser(Long id) {
        log.info("Eliminando usuario por ID: {}", id);
        return userRepository.deleteById(id);
    }
}
