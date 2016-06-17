package ru.troshkov.db;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.troshkov.db.domain.*;
import ru.troshkov.db.domain.constants.PeopleStatus;
import ru.troshkov.db.repo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class KursachApplication implements CommandLineRunner {
	private static final int TABLE_SIZE = 100_000;
	private static final boolean POPULATE = false;

	private static final Logger log = Logger.getLogger(CommandLineRunner.class);

	private static Random random = new Random();

	private LoremIpsum loremIpsum = new LoremIpsum();

	@Autowired
	private ArenaRepo arenaRepo;
	@Autowired
	private CrimeRepo crimeRepo;
	@Autowired
	private FightRepo fightRepo;
	@Autowired
	private PeopleRepo peopleRepo;
	@Autowired
	private TypeRepo typeRepo;
	@Autowired
	private PrisonerRepo prisonerRepo;

	public static void main(String[] args) {
		SpringApplication.run(KursachApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(POPULATE) {
			arenaRepo.deleteAll();
			crimeRepo.deleteAll();
			fightRepo.deleteAll();
			peopleRepo.deleteAll();
			typeRepo.deleteAll();
			prisonerRepo.deleteAll();

			log.info("Population start");

			List<Arena> arenas = new ArrayList<>(TABLE_SIZE);
			List<Crime> crimes = new ArrayList<>(TABLE_SIZE);
			List<Fight> fights = new ArrayList<>(TABLE_SIZE);
			List<People> peoples = new ArrayList<>(TABLE_SIZE);
			List<Prisoner> prisoners = new ArrayList<>(TABLE_SIZE);
			List<Type> types = new ArrayList<>(TABLE_SIZE);

			log.info("Arena gen start");
			while (arenas.size() < TABLE_SIZE) {
				Arena arena = new Arena();

				arena.setName(generateWord(true));
				arena.setCapacity((long) random.nextInt(42_000));

				arenas.add(arena);
			}
			log.info("Arena gen done");

			log.info("Arena populate start");
			arenaRepo.insert(arenas);
			arenas = arenaRepo.findAll();
			log.info("Arena populate done");

			log.info("Type gen start");
			while (types.size() < TABLE_SIZE) {
				Type type = new Type();

				type.setName(generateWord(true));
				type.setWeapon(generateWord(true));

				types.add(type);
			}
			log.info("Type gen done");

			log.info("Type populate start");
			typeRepo.insert(types);
			types = typeRepo.findAll();
			log.info("Type populate done");

			log.info("Crimes gen start");
			while (crimes.size() < TABLE_SIZE) {
				Crime crime = new Crime();

				crime.setDescription(loremIpsum.getParagraphs(random.nextInt(3) + 1));
				crime.setTerm(random.nextInt(99) + random.nextDouble());
				crime.setWeight((byte) random.nextInt(5));

				crimes.add(crime);
			}
			log.info("Crimes gen done");

			log.info("Crimes populate start");
			crimeRepo.insert(crimes);
			crimes = crimeRepo.findAll();
			log.info("Crimes populate done");

			log.info("People gen start");
			while (peoples.size() < TABLE_SIZE) {
				People people = new People();

				people.setFirstName(generateWord(true));
				people.setLastName(generateWord(true));
				people.setMiddleName(generateWord(true));
				switch (random.nextInt(5)) {
					case 0:
						people.setStatus(PeopleStatus.PRISONER);
						break;
					case 1:
						people.setStatus(PeopleStatus.FIGHTER);
						break;
					case 2:
						people.setStatus(PeopleStatus.TURNKEY);
						break;
					case 4:
						people.setStatus(PeopleStatus.JUDGE);
						break;
					default:
						people.setStatus(PeopleStatus.PRISONER);
						break;
				}
				people.setBirthday(new Date(20_000_000+random.nextInt(4200_000)));

				peoples.add(people);
			}
			log.info("People gen done");

			log.info("People populate start");
			peopleRepo.insert(peoples);
			peoples = peopleRepo.findAll();
			log.info("People populate done");

			log.info("Prisoner gen start");
			for (People people : peopleRepo.findAll()) {
				if (people.getStatus() == PeopleStatus.FIGHTER || people.getStatus() == PeopleStatus.PRISONER) {
					Prisoner prisoner = new Prisoner();

					prisoner.setPeople(people);
					prisoner.setCrime(crimes.get(random.nextInt(crimes.size())));
					prisoner.setStart(new Date(20_000_000 + 4_200_000 + random.nextInt(4_200_00)));
					prisoner.setEnd(new Date(prisoner.getStart().getTime() + random.nextInt(4_200_00)));
					prisoner.setFighterType(people.getStatus() == PeopleStatus.FIGHTER ? types.get(random.nextInt(types.size())) : null);

					prisoners.add(prisoner);
				}
			}
			log.info("Prisoner gen done");

			log.info("Prisoner populate start");
			prisonerRepo.insert(prisoners);
			log.info("Prisoner populate done");

			log.info("Fight gen start");
			while (fights.size() < TABLE_SIZE) {
				Fight fight = new Fight();

				fight.setName(generateWord(true));
				fight.setArena(arenas.get(random.nextInt(arenas.size())));
				fight.setFirst(peoples.get(random.nextInt(peoples.size())));
				if(fight.getFirst().getStatus() != PeopleStatus.FIGHTER)
					continue;
				fight.setSecond(peoples.get(random.nextInt(peoples.size())));
				if(fight.getSecond().getStatus() != PeopleStatus.FIGHTER)
					continue;
				fight.setDate(new Date(20_000_000 + 4_200_000 + random.nextInt(4_200_00) + random.nextInt(4_200_00)));
				fight.setFirstResult(random.nextBoolean());
				fight.setSecondResult(random.nextBoolean());
				fight.setJudge(peoples.get(random.nextInt(peoples.size())));
				if(fight.getJudge().getStatus() != PeopleStatus.JUDGE)
					continue;

				fights.add(fight);
			}
			log.info("Fight gen done");

			log.info("Fight populate start");
			fightRepo.insert(fights);
			log.info("Fight populate done");

			log.info("Population done");
		}
	}

	private static String generateWord(boolean uppercase) {
		StringBuilder word = new StringBuilder(RandomStringUtils.randomAlphabetic(random.nextInt(5)+6).toLowerCase());

		if(uppercase) {
			word.setCharAt(0, Character.toUpperCase(word.charAt(0)));
		}

		return word.toString();
	}
}
