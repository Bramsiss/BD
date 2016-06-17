package ru.troshkov.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.troshkov.db.domain.Fight;
import ru.troshkov.db.domain.Type;

/**
 * Created by ivan on 17.06.2016.
 */
public interface TypeRepo extends MongoRepository<Type, String> {
}
