package com.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.Media;
import com.rraiz.movie_critic.model.Person;
import com.rraiz.movie_critic.service.CastService;
import com.rraiz.movie_critic.service.MediaService;
import com.rraiz.movie_critic.service.PersonService;

@Component
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
        // Parse media data from mediaLine and set attributes of Media object
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
        // Parse cast data from castLine and set attributes of Cast object
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
        // Parse person data from personLine and set attributes of Person object
        return person;
    }
}
