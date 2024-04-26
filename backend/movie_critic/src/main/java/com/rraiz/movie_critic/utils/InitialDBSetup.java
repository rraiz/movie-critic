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
import com.rraiz.movie_critic.model.CastId;
import com.rraiz.movie_critic.model.Media;
import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.service.CastService;
import com.rraiz.movie_critic.service.MediaService;
import com.rraiz.movie_critic.service.PersonService;


@ComponentScan(basePackages = "com.rraiz.movie_critic")
@SpringBootApplication
@Component
public class InitialDBSetup implements CommandLineRunner{

    @FunctionalInterface
    interface ParseEntityLineFunction {
        void call(String line);
    }

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
            System.out.println("Setting up the cast table");
            setupEntity(castFilePath, this::parseCastLine);
            System.out.println("Setting up the media table");
            setupEntity(mediaFilePath, this::parseMediaLine);
            System.out.println("Setting up the person table");
            setupEntity(personFilePath, this::parsePersonLine);
            System.out.println("Database setup complete");
        } catch (IOException e) {
            System.out.println("Error in setting up the database");
            e.printStackTrace();
        }
    }

    private void setupEntity(String filePath, ParseEntityLineFunction parseEntityLine) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip the first line
            String line;
            while ((line = br.readLine()) != null) {
                parseEntityLine.call(line);
            }
        }
    }

    private void parseMediaLine(String mediaLine) {
        String[] mediaData = mediaLine.split("\t");

        String t_const = '1' + mediaData[0].substring(2);
        Media media = mediaService.getMediaById(Integer.parseInt(t_const));

        if (media == null) {
            media = new Media();
            media.setTconst(Integer.parseInt(t_const));
        }

        media.setTitleType(mediaData[1]);
        media.setTitle(mediaData[2]);
        media.setStartYear(mediaData[3].equals("\\N") ? null : Integer.valueOf(mediaData[3]));
        media.setEndYear(mediaData[4].equals("\\N") ? null : Integer.valueOf(mediaData[4]));
        media.setRuntimeMinutes(mediaData[5].equals("\\N") ? null : Integer.valueOf(mediaData[5]));
        media.setGenres( mediaData[6].equals("\\N") ? null : Arrays.asList(mediaData[6].split(",")));
        media.setAverageRating(mediaData[7].equals("\\N") ? null : Double.valueOf(mediaData[7]));
        media.setNumVotes(mediaData[8].equals("\\N") ? null : Integer.valueOf(mediaData[8]));

        mediaService.addMedia(media);
    }

    private void parseCastLine(String castLine) {
        String[] castData = castLine.split("\t");

        String t_const_str = '1' + castData[0].substring(2);
        String n_const_str = '2' + castData[1].substring(2);

        int t_const = Integer.parseInt(t_const_str);
        int n_const = Integer.parseInt(n_const_str);
        
        CastId id = new CastId(t_const, n_const);
        Cast cast = castService.getCastById(id);

        if (cast == null) {
            cast = new Cast();
            cast.setId(id);
        }

        Media media = mediaService.addForeignKey(t_const);
        Person person = personService.addForeignKey(n_const);

        cast.setMedia(media);
        cast.setPerson(person);
        cast.setCategory(castData[2]);
        cast.setJob(castData[3].equals("\\N") ? null : castData[3]);
        cast.setCharacters(castData[4].equals("\\N") ? null : Arrays.asList(castData[4].split(","))); 
        
        castService.addCast(cast);
    }

    private void parsePersonLine(String personLine) {
        String[] personData = personLine.split("\t");

        String n_const = '2' + personData[0].substring(2);
        Person person = personService.getPersonById(Integer.parseInt(n_const));

        if (person == null) {
            person = new Person();
            person.setNconst(Integer.parseInt(n_const));
        }

        person.setName(personData[1]);
        person.setBirthYear(personData[2].equals("\\N") ? null : Integer.valueOf(personData[2]));
        person.setDeathYear(personData[3].equals("\\N") ? null : Integer.valueOf(personData[3]));
        person.setPrimaryProfession(personData[4].equals("\\N") ? null : Arrays.asList(personData[4].split(",")));

        personService.addPerson(person);
    }

    @Override
    public void run(String... args) throws Exception {
        setupDatabase();
    } 

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(InitialDBSetup.class, args);
    }
}
