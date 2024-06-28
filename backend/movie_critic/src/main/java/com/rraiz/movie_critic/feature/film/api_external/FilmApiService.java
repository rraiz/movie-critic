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
import com.rraiz.movie_critic.feature.film.model.entity.ProductionCompany;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.feature.film.service.MovieService;
import com.rraiz.movie_critic.feature.film.service.PersonService;
import com.rraiz.movie_critic.feature.film.service.ProductionCompanyService;
import com.rraiz.movie_critic.feature.film.service.TvShowService;
import com.rraiz.movie_critic.util.ApiUtil;

@Service
public class FilmApiService {

    private final ApiUtil apiUtil;
    private final PersonApiService personApiService;

    private final ProductionCompanyService productionCompanyService;
    private final PersonService personService;
    private final MovieService movieService;
    private final TvShowService tvShowService;

    public FilmApiService(ApiUtil apiUtil, PersonApiService personApiService,
            ProductionCompanyService productionCompanyService, @Lazy PersonService personService, MovieService movieService,
            TvShowService tvShowService) {
        this.apiUtil = apiUtil;
        this.personApiService = personApiService;
        this.productionCompanyService = productionCompanyService;
        this.personService = personService;
        this.movieService = movieService;
        this.tvShowService = tvShowService;
    }

    @SuppressWarnings("unchecked")
    public Film mapFilm(JsonNode root, Film film) {
        mapApiResponseToFilm(root, film);
        Object[] credits = fetchFilmCredits(film.getId().getFilmId(), film.getId().getFilmType());

        Set<Cast> cast = (Set<Cast>) credits[0];
        Set<Crew> crew = (Set<Crew>) credits[1];

        film.setCrew(crew);
        film.setCast(cast);
        film.setLastUpdated(LocalDate.now());

        return film;
    }

    public Object[] fetchFilmCredits(int filmId, int filmType) {
        String endpoint = "/%s/%d/credits".formatted(filmType == 0 ? "movie" : "tv", filmId);
        if (filmType == 0) {
            return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToMovieCredits);
        } else {
            return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToTvShowCredits);
        }
    }

    /**
     * Maps the JSON response from the API to a Film object.
     * This method is used by both Movie and TvShow mapping methods to map common
     * fields.
     * 
     * @param root JSON response from the API
     * @param film Film object to map the response to
     */
    private void mapApiResponseToFilm(JsonNode root, Film film) {

        mapCommonFilmAttributes(film, root);

        List<String> genres = root.get("genres").findValues("name").stream().map(JsonNode::asText)
                .collect(Collectors.toList());
        List<String> productionCountries = root.get("production_countries").findValues("name").stream()
                .map(JsonNode::asText).collect(Collectors.toList());
        List<String> spokenLanguages = root.get("spoken_languages").findValues("english_name").stream()
                .map(JsonNode::asText).collect(Collectors.toList());
        List<String> originCountries = StreamSupport.stream(root.get("origin_country").spliterator(), false)
                .map(JsonNode::asText).collect(Collectors.toList());

        // Map production companies if available
        Set<ProductionCompany> produced_set = mapFilmProductionCompanies(root, film);

        film.setHomepage(apiUtil.getValueAsText(root.get("homepage")));
        film.setTagline(apiUtil.getValueAsText(root.get("tagline")));
        film.setGenres(genres);
        film.setProductionCountries(productionCountries);
        film.setSpokenLanguages(spokenLanguages);
        film.setOriginCountries(originCountries);

        film.setProduced(produced_set);
    }

    private Set<ProductionCompany> mapFilmProductionCompanies(JsonNode root, Film film) {
        if (root.get("production_companies").isNull()) {
            return null;
        }

        Set<ProductionCompany> produced_set = new HashSet<>(); // Creates a set of produced movies

        // Fore each production company
        for (JsonNode company : root.get("production_companies")) {
            // Extracts the id of the company
            int companyId = company.get("id").asInt();

            // Tries to find the company in the database
            ProductionCompany productionCompany = productionCompanyService.getProductionCompanyById(companyId);
            if (productionCompany == null) { // if not found then
                productionCompany = new ProductionCompany(); // creates a new one
            }
            productionCompany.setId(companyId); // Sets id
            productionCompany.setName(company.get("name").asText()); // name
            productionCompany.setLogoPath(company.get("logo_path").asText()); // logo path
            productionCompany.setOriginCountry(company.get("origin_country").asText()); // origin country
            productionCompanyService.addProductionCompany(productionCompany); // Saves the company to db

            Set<Film> producedFilm = productionCompany.getProduced(); // Gets the set of produced movies
            if (producedFilm == null) // If the set is null
                producedFilm = new HashSet<>(); // Creates a new set
            producedFilm.add(film); // Adds the film to the set
            productionCompany.setProduced(producedFilm); // Sets the set to the production company

            productionCompanyService.addProductionCompany(productionCompany); // Saves the production company to db
            produced_set.add(productionCompany); // Adds the production company to the set
        }
        return produced_set; // Returns the set of production companies
    }

    private Object[] mapApiResponseToMovieCredits(JsonNode root) {
        return mapApiResponseToFilmCredits(root, 0);
    }

    private Object[] mapApiResponseToTvShowCredits(JsonNode root) {
        return mapApiResponseToFilmCredits(root, 1);
    }

    private Object[] mapApiResponseToFilmCredits(JsonNode root, int filmType) {
        Set<Cast> cast = null;
        Set<Crew> crew = null;

        int filmId = root.get("id").asInt();
        Film film = getFilmById(filmId, filmType);
        if (film == null) {
            film = createNewFilm(filmType);
        }
        film.setId(new FilmId(filmId, filmType));
        addFilmToDatabase(film, filmType);


        if (!root.get("cast").isNull()) {
            cast = new HashSet<>();
            for (JsonNode castNode : root.get("cast")) {

                // Maps person and saves object
                Person person = mapApiResponseToPerson(castNode);
                personService.addPerson(person);

                // Maps cast and saves object
                Cast castMember = personApiService.mapCast(person, film, castNode);
                personService.addCast(castMember);

                // Adds the cast member to the person object
                Set<Cast> played_in = person.getCast();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(castMember);
                person.setCast(played_in);
                personService.addPerson(person);

                // Adds the cast member to the cast list for the film object
                cast.add(castMember);
            }
        }

        if (!root.get("crew").isNull()) {
            crew = new HashSet<>();
            for (JsonNode crewNode : root.get("crew")) {

                // Maps person and saves object
                Person person = mapApiResponseToPerson(crewNode);
                personService.addPerson(person);

                // Maps cast and saves object
                Crew crewMember = personApiService.mapCrew(person, film, crewNode);
                personService.addCrew(crewMember);

                // Adds the crew member to the person object
                Set<Crew> played_in = person.getCrew();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(crewMember);
                person.setCrew(played_in);
                personService.addPerson(person);

                // Adds the crew member to the crew list for the film object
                crew.add(crewMember);
            }
        }
        Object[] credits = { cast, crew };
        return credits;
    }

    private Person mapApiResponseToPerson(JsonNode personNode) {
        int personId = personNode.get("id").asInt();
        Person person = personService.getPersonById(personId);
        if (person == null) {
            person = new Person();
            person.setId(personId);
        }
        personApiService.mapCommonPersonAttributes(person, personNode);
        return person;
    }

    public void mapCommonFilmAttributes(Film film, JsonNode filmNode) {
        film.setAdult(apiUtil.getValueAsBoolean(filmNode.get("adult")));
        film.setBackdropPath(apiUtil.getValueAsText(filmNode.get("backdrop_path")));
        film.setOriginalLanguage(apiUtil.getValueAsText(filmNode.get("original_language")));
        film.setOriginalName(apiUtil.getValueAsText(filmNode.get("original_title")));
        film.setOverview(apiUtil.getValueAsText(filmNode.get("overview")));
        film.setPopularity(apiUtil.getValueAsDouble(filmNode.get("popularity")));
        film.setPosterPath(apiUtil.getValueAsText(filmNode.get("poster_path")));
        film.setTitle(apiUtil.getValueAsTitleOrName(filmNode));
        film.setVoteAverage(apiUtil.getValueAsDouble(filmNode.get("vote_average")));
        film.setVoteCount(apiUtil.getValueAsInt(filmNode.get("vote_count")));
    }

    public Film getFilmById(int filmId, int filmType) {
        if (filmType == 0) {
            return movieService.getMovieById(filmId);
        } else {
            return tvShowService.getTvShowById(filmId);
        }
    }

    public Film createNewFilm(int filmType) {
        if (filmType == 0) {
            return new Movie();
        } else {
            return new TvShow();
        }
    }
    
    public void addFilmToDatabase(Film film, int filmType) {
        if (filmType == 0) {
            movieService.addMovie((Movie) film);
        } else {
            tvShowService.addTvShow((TvShow) film);
        }
    }

}
