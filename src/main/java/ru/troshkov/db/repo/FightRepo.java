package ru.troshkov.db.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.troshkov.db.domain.Crime;
import ru.troshkov.db.domain.Fight;

/**
 * Created by ivan on 17.06.2016.
 */
public interface FightRepo extends MongoRepository<Fight, String> {
    Page<Fight> findByNameContains(String name, Pageable pageable);
}
