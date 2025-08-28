package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.commons.AlreadyExistsException;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    /**
     * Guarda un usuario validando los campos requeridos y la unicidad del email.
     */
    public Mono<User> saveUser(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists.booleanValue()) {
                        return Mono.error(new AlreadyExistsException("El correo ya está registrado"));
                    }
                    return userRepository.save(user);
                });
    }

    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}
