package com.rraiz.movie_critic.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.Media;
import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.service.CastService;
import com.rraiz.movie_critic.service.MediaService;
import com.rraiz.movie_critic.service.PersonService;


public class InitialDBSetup {

    private final MediaService mediaService;
    private final CastService castService;
    private final PersonService personService;

    public InitialDBSetup(MediaService mediaService, CastService castService, PersonService personService) {
        this.mediaService = mediaService;
        this.castService = castService;
        this.personService = personService;
    }

    public void setupDatabase() {
        String mediaFilePath = "/tsv_initialize/media.tsv";
        String castFilePath = "/tsv_initialize/media-cast.tsv";
        String personFilePath = "/tsv_initialize/names.tsv";

        try {
            setupMedia(mediaFilePath);
            setupCast(castFilePath);
            setupPerson(personFilePath);
        } catch (IOException e) {
            System.out.println("Error in setting up the database");
            e.printStackTrace();
        }
    }

    private void setupMedia(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Media media = parseMediaLine(line);
                mediaService.addMedia(media);
            }
        }
    }

    private Media parseMediaLine(String mediaLine) {
        String[] mediaData = mediaLine.split("\t");
        Media media = new Media();
        media.setTconst(mediaData[0]);
        media.setTitleType(mediaData[1]);
        media.setTitle(mediaData[2]);
        media.setStartYear(mediaData[3].equals("\\N") ? null : Integer.valueOf(mediaData[3]));
        media.setEndYear(mediaData[4].equals("\\N") ? null : Integer.valueOf(mediaData[4]));
        media.setRuntimeMinutes(mediaData[5].equals("\\N") ? null : Integer.valueOf(mediaData[5]));
        media.setGenres( mediaData[6].equals("\\N") ? null : Arrays.asList(mediaData[6].split(",")));
        media.setAverageRating(mediaData[7].equals("\\N") ? null : Double.valueOf(mediaData[7]));
        media.setNumVotes(mediaData[8].equals("\\N") ? null : Integer.valueOf(mediaData[8]));
        return media;
    }

    private void setupCast(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Cast cast = parseCastLine(line);
                castService.addCast(cast);
            }
        }
    }

    private Cast parseCastLine(String castLine) {
        String[] castData = castLine.split("\t");
        Cast cast = new Cast();
        cast.setTconst(castData[0]);
        cast.setNconst(castData[1]);
        cast.setCategory(castData[2]);
        cast.setJob(castData[3].equals("\\N") ? null : castData[3]);
        cast.setCharacters(castData[4].equals("\\N") ? null : Arrays.asList(castData[4].split(",")));
        return cast;
    }

    private void setupPerson(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Person person = parsePersonLine(line);
                personService.addPerson(person);
            }
        }
    }

    private Person parsePersonLine(String personLine) {
        String[] personData = personLine.split("\t");
        Person person = new Person();
        person.setNconst(personData[0]);
        person.setName(personData[1]);
        person.setBirthYear(personData[2].equals("\\N") ? null : Integer.valueOf(personData[2]));
        person.setDeathYear(personData[3].equals("\\N") ? null : Integer.valueOf(personData[3]));
        person.setPrimaryProfession(personData[4].equals("\\N") ? null : Arrays.asList(personData[4].split(",")));
        person.setKnownForTitles(personData[5].equals("\\N") ? null : Arrays.asList(personData[5].split(",")));
        return person;
    }

 public static void main(String[] args) {
        // Initialize the Spring application context
        ApplicationContext context = SpringApplication.run(InitialDBSetup.class);

        // Retrieve the necessary services from the context
        MediaService mediaService = context.getBean(MediaService.class);
        CastService castService = context.getBean(CastService.class);
        PersonService personService = context.getBean(PersonService.class);

        // Create an instance of InitialDBSetup with the retrieved services
        InitialDBSetup dbSetup = new InitialDBSetup(mediaService, castService, personService);

        // Call the setupDatabase method to populate the database
        dbSetup.setupDatabase();
    }
}
