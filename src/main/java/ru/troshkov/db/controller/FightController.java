package ru.troshkov.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.troshkov.db.domain.Fight;
import ru.troshkov.db.repo.FightRepo;

/**
 * Created by ivan on 17.06.2016.
 */
@RestController
@RequestMapping(path = "/api/fight")
public class FightController {
    @Autowired
    FightRepo fightRepo;

    @RequestMapping("/")
    public Page<Fight> findAll(@PageableDefault Pageable pageable) {
        return fightRepo.findAll(pageable);
    }

    @RequestMapping("/name/{name}")
    public Page<Fight> findAll(@PathVariable String name, @PageableDefault Pageable pageable) {
        return fightRepo.findByNameContains(name, pageable);
    }
}
