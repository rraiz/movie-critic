package com.rraiz.movie_critic.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.Media;
import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.service.CastService;
import com.rraiz.movie_critic.service.MediaService;
import com.rraiz.movie_critic.service.PersonService;


@ComponentScan(basePackages = "com.rraiz.movie_critic")
@SpringBootApplication
@Component
public class InitialDBSetup implements CommandLineRunner{

    private final MediaService mediaService;
    private final CastService castService;
    private final PersonService personService;

    public InitialDBSetup(MediaService mediaService, CastService castService, PersonService personService) {
        this.mediaService = mediaService;
        this.castService = castService;
        this.personService = personService;
    }

    public void setupDatabase() {
        String mediaFilePath = "backend/movie_critic/src/main/java/com/rraiz/movie_critic/utils/tsv_initialize/media.tsv";
        String castFilePath = "backend/movie_critic/src/main/java/com/rraiz/movie_critic/utils/tsv_initialize/media-cast.tsv";
        String personFilePath = "backend/movie_critic/src/main/java/com/rraiz/movie_critic/utils/tsv_initialize/names.tsv";

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
            br.readLine(); // skip the first line
            String line = br.readLine();
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
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip the first line
            String line;
            while ((line = br.readLine()) != null) {
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
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip the first line
            String line;
            while ((line = br.readLine()) != null) {
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

    @Override
    public void run(String... args) throws Exception {
        setupDatabase();
    } 

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(InitialDBSetup.class, args);
    }
}
