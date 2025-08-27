package co.com.pragma.r2dbc.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserDataRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        Long,
        UserDataRepository
> implements UserRepository {
    public UserDataRepositoryAdapter(UserDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, User.UserBuilder.class).build());
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return super.repository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return super.repository.existsByEmail(email);
    }
}
