package ru.troshkov.db.controller;

import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NGram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.troshkov.db.domain.Fight;
import ru.troshkov.db.repo.FightRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Page<Fight> findName(@PathVariable String name, @PageableDefault Pageable pageable) {

        return fightRepo.findByNameContains(name, pageable);
    }

    @RequestMapping("/l-name/{name}")
    public Page<Fight> findLName(@PathVariable String name, @PageableDefault Pageable pageable) {
        Levenshtein l = new Levenshtein();

        List<Fight> fights = fightRepo.findAll().stream().filter(fight -> l.distance(fight.getName(), name) < 3).collect(Collectors.toList());

        return new PageImpl<>(fights, pageable, fights.size());
    }

    @RequestMapping("/j-w-name/{name}")
    public Page<Fight> findJWName(@PathVariable String name, @PageableDefault Pageable pageable) {
        JaroWinkler jw = new JaroWinkler();

        List<Fight> fights = fightRepo.findAll().stream().filter(fight -> jw.similarity(name, fight.getName()) >= 0.8).collect(Collectors.toList());

        return new PageImpl<>(fights, pageable, fights.size());
    }

    @RequestMapping("/n-name/{name}")
    public Page<Fight> findNName(@PathVariable String name, @PageableDefault Pageable pageable) {
        NGram n = new NGram(2);

        List<Fight> fights = fightRepo.findAll().stream().filter(fight -> n.distance(name, fight.getName()) >= 0.6).collect(Collectors.toList());

        return new PageImpl<>(fights, pageable, fights.size());
    }
}
