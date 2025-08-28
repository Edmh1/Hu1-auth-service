package co.com.pragma.api;

import co.com.pragma.api.dto.UserRequestDto;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.commons.AlreadyExistsException;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private  final UserUseCase useCase;


    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(useCase.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserRequestDto.class)
                .flatMap(dto -> {
                    User user = User.builder()
                            .name(dto.getName())
                            .lastName(dto.getLastName())
                            .birthDate(dto.getBirthDate())
                            .address(dto.getAddress())
                            .phone(dto.getPhone())
                            .email(dto.getEmail())
                            .baseSalary(dto.getBaseSalary())
                            .build();
                    return useCase.saveUser(user);
                })
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(e -> {
                    if (e instanceof AlreadyExistsException) {
                        return ServerResponse.status(409).bodyValue(e.getMessage());
                    }
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }

}
