package ru.troshkov.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.troshkov.db.domain.Fight;
import ru.troshkov.db.domain.People;
import ru.troshkov.db.repo.FightRepo;
import ru.troshkov.db.repo.PeopleRepo;

/**
 * Created by ivan on 17.06.2016.
 */
@RestController
@RequestMapping(path = "/api/people")
public class PeopleController {
    @Autowired
    PeopleRepo peopleRepo;

    @RequestMapping("/")
    public Page<People> findAll(@PageableDefault Pageable pageable) {
        return peopleRepo.findAll(pageable);
    }
}
