package com.rraiz.movie_critic.feature.film.api_external;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rraiz.movie_critic.feature.film.model.entity.Cast;
import com.rraiz.movie_critic.feature.film.model.entity.Crew;
import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.Person;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.feature.film.model.identifier.CastId;
import com.rraiz.movie_critic.feature.film.model.identifier.CrewId;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.feature.film.service.MovieService;
import com.rraiz.movie_critic.feature.film.service.PersonService;
import com.rraiz.movie_critic.feature.film.service.TvShowService;
import com.rraiz.movie_critic.util.ApiUtil;

@Service
public class PersonApiService {

    private final ApiUtil apiUtil;
    private final PersonService personService;
    private final MovieService movieService;
    private final TvShowService tvShowService;

    private final FilmApiService filmApiService;

    public PersonApiService(ApiUtil apiUtil, PersonService personService, MovieService movieService,
            TvShowService tvShowService, @Lazy FilmApiService filmApiService) {
        this.apiUtil = apiUtil;
        this.personService = personService;
        this.movieService = movieService;
        this.tvShowService = tvShowService;
        this.filmApiService = filmApiService;
    }

    @SuppressWarnings("unchecked")
    public Person fetchPerson(int personId) {
        Person person = fetchPersonDetails(personId);
        if (person == null) {
            return null;
        }
        Object[] credits = fetchPersonCreditsDetails(person.getId());

        Set<Cast> cast = (Set<Cast>) credits[0];
        Set<Crew> crew = (Set<Crew>) credits[1];

        person.setCast(cast);
        person.setCrew(crew);
        person.setLastUpdated(LocalDate.now());
        personService.addPerson(person);

        return person;
    }

    public Person fetchPersonDetails(int personId) {
        String endpoint = "/person/%d".formatted(personId);
        return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToPerson);
    }

    public Object[] fetchPersonCreditsDetails(int personId) {
        String endpoint = "/person/%d/combined_credits".formatted(personId);
        return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToPersonCredits);
    }

    private Person mapApiResponseToPerson(JsonNode root) {
        Person person = personService.getPersonById(root.get("id").asInt());
        if (person == null) {
            person = new Person();
            person.setId(root.get("id").asInt());
            personService.addPerson(person);
        }

        mapCommonPersonAttributes(person, root);
        person.setBiography(apiUtil.getValueAsText(root.get("biography")));
        person.setBirthDate(apiUtil.getValueAsLocalDate(root.get("birthday")));
        person.setDeathDate(apiUtil.getValueAsLocalDate(root.get("deathday")));
        person.setBirthPlace(apiUtil.getValueAsText(root.get("place_of_birth")));
        person.setImdbId(apiUtil.getValueAsText(root.get("imdb_id")));
        person.setHomepage(apiUtil.getValueAsText(root.get("homepage")));

        List<String> also_known_as = StreamSupport.stream(root.get("also_known_as").spliterator(), false)
                .map(JsonNode::asText).collect(Collectors.toList());
        person.setAlsoKnownAs(also_known_as);

        person.setLastUpdated(LocalDate.now());
        personService.addPerson(person);

        return person;
    }

    private Object[] mapApiResponseToPersonCredits(JsonNode root) {
        Set<Cast> cast = null;
        Set<Crew> crew = null;

        int personId = root.get("id").asInt();
        Person person = personService.getPersonById(personId);
        if (person == null) {
            person = new Person();
        }
        person.setId(personId);
        personService.addPerson(person);

        if (!root.get("cast").isNull()) {
            cast = new HashSet<>();
            for (JsonNode castNode : root.get("cast")) {

                Film film = mapApiResponseToFilm(castNode);
                Cast castMember = mapCast(person, film, castNode);
                personService.addCast(castMember);

                // Adding the cast member to the film object
                Set<Cast> played_in = film.getCast();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(castMember);
                film.setCast(played_in);
                if (film.getId().getFilmType() == 0) {
                    movieService.addMovie((Movie) film);
                } else {
                    tvShowService.addTvShow((TvShow) film);
                }

                cast.add(castMember);
            }
        }

        if (!root.get("crew").isNull()) {
            crew = new HashSet<>();
            for (JsonNode crewNode : root.get("crew")) {

                // Film logic
                Film film = mapApiResponseToFilm(crewNode);
                Crew crewMember = mapCrew(person, film, crewNode);

                personService.addCrew(crewMember);

                // Adding the crew member to the film object
                Set<Crew> played_in = film.getCrew();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(crewMember);
                film.setCrew(played_in);
                if (film.getId().getFilmType() == 0) {
                    movieService.addMovie((Movie) film);
                } else {
                    tvShowService.addTvShow((TvShow) film);
                }

                crew.add(crewMember);
            }
        }

        Object[] credits = { cast, crew };
        return credits;
    }



    private Film mapApiResponseToFilm(JsonNode filmNode) {
        // Film logic
        int media_type = filmNode.get("media_type").asText().equals("movie") ? 0 : 1;
        int filmId = filmNode.get("id").asInt();
        FilmId filmIdObj = new FilmId(filmId, media_type);
        Film film = media_type == 0 ? movieService.getMovieById(filmId) : tvShowService.getTvShowById(filmId);
        if (film == null) {
            film = media_type == 0 ? new Movie() : new TvShow();
        }
        film.setId(filmIdObj);
        if (media_type == 0) {
            movieService.addMovie((Movie) film);
        } else {
            tvShowService.addTvShow((TvShow) film);
        }
        filmApiService.mapCommonFilmAttributes(film, filmNode);
        return film;
    }

    public void mapCommonPersonAttributes(Person person, JsonNode personNode) {
        person.setId(person.getId());
        person.setName(apiUtil.getValueAsText(personNode.get("name")));
        person.setGender(apiUtil.getValueAsInt(personNode.get("gender")));
        person.setAdult(apiUtil.getValueAsBoolean(personNode.get("adult")));
        person.setKnownFor(apiUtil.getValueAsText(personNode.get("known_for_department")));
        person.setPopularity(apiUtil.getValueAsDouble(personNode.get("popularity")));
        person.setProfilePath(apiUtil.getValueAsText(personNode.get("profile_path")));
    }
    
    public Cast mapCast(Person person, Film film, JsonNode castNode) {
        CastId castId = new CastId(person.getId(), film.getId());
        Cast castMember = personService.getCastById(castId);
        if (castMember == null) {
            castMember = new Cast();
        }
        castMember.setId(castId);
        castMember.setCharacter(apiUtil.getValueAsText(castNode.get("character")));
        castMember.setOrdering(apiUtil.getValueAsInt(castNode.get("order")));
        castMember.setPerson(person);
        castMember.setFilm(film);
        return castMember;
    }

    public Crew mapCrew(Person person, Film film, JsonNode crewNode) {
        String department = apiUtil.getValueAsText(crewNode.get("department"));
        String job = apiUtil.getValueAsText(crewNode.get("job"));
        if (department == null) {
            department = "Unknown";
        }
        if (job == null) {
            job = "Unknown";
        }

        CrewId crewId = new CrewId(person.getId(), film.getId(), department, job);
        Crew crewMember = personService.getCrewById(crewId);
        if (crewMember == null) {
            crewMember = new Crew();
        }
        crewMember.setId(crewId);
        crewMember.setPerson(person);
        crewMember.setFilm(film);
        return crewMember;
    }


}
