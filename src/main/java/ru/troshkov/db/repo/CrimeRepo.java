package ru.troshkov.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.troshkov.db.domain.Arena;
import ru.troshkov.db.domain.Crime;

/**
 * Created by ivan on 17.06.2016.
 */
public interface CrimeRepo extends MongoRepository<Crime, String> {
}
