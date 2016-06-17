package ru.troshkov.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.troshkov.db.domain.Arena;

/**
 * Created by ivan on 17.06.2016.
 */
public interface ArenaRepo extends MongoRepository<Arena, String> {
}
