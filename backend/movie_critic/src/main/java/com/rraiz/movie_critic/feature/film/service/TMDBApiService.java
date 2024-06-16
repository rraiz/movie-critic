package com.rraiz.movie_critic.feature.film.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rraiz.movie_critic.feature.film.model.entity.Cast;
import com.rraiz.movie_critic.feature.film.model.entity.Collection;
import com.rraiz.movie_critic.feature.film.model.entity.Crew;
import com.rraiz.movie_critic.feature.film.model.entity.Episode;
import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.Network;
import com.rraiz.movie_critic.feature.film.model.entity.Person;
import com.rraiz.movie_critic.feature.film.model.entity.ProductionCompany;
import com.rraiz.movie_critic.feature.film.model.entity.Season;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.feature.film.model.identifier.CastId;
import com.rraiz.movie_critic.feature.film.model.identifier.CrewId;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;

@Service
public class TMDBApiService {

    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private static final Logger logger = LoggerFactory.getLogger(TMDBApiService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    private final MovieService movieService;
    private final TvShowService tvShowService;
    private final CollectionService collectionService;
    private final ProductionCompanyService productionCompanyService;
    private final PersonService personService;
    private final NetworkService networkService;

    public TMDBApiService(RestTemplateBuilder restTemplateBuilder, @Value("${TMDB_API_KEY}") String apiKey,
            MovieService movieService, TvShowService tvShowService, CollectionService collectionService,
            ProductionCompanyService productionCompanyService, PersonService personService,
            NetworkService networkService) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
        this.movieService = movieService;
        this.tvShowService = tvShowService;
        this.collectionService = collectionService;
        this.productionCompanyService = productionCompanyService;
        this.personService = personService;
        this.networkService = networkService;
    }

    private <T> T fetchFromApi(String url, Function<JsonNode, T> mappingFunction) {
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            return mappingFunction.apply(root);
        } catch (Exception e) {
            logger.error("Error fetching data from URL {}: {}", url, e.getMessage());
            return null;
        }
    }

    public Object[] fetchPersonCredits(int personId) {
        String url = String.format("%s/person/%d/combined_credits?api_key=%s", TMDB_API_BASE_URL, personId, apiKey);
        return fetchFromApi(url, this::mapApiResponseToPersonCredits);
    }

    public Person fetchPersonDetails(int personId) {
        String url = String.format("%s/person/%d?api_key=%s", TMDB_API_BASE_URL, personId, apiKey);
        return fetchFromApi(url, this::mapApiResponseToPerson);
    }

    public Movie fetchMovieDetails(int movieId) {
        String url = String.format("%s/movie/%d?api_key=%s", TMDB_API_BASE_URL, movieId, apiKey);
        return fetchFromApi(url, this::mapApiResponseToMovie);
    }

    public TvShow fetchTvShowDetails(int tvShowId) {
        String url = String.format("%s/tv/%d?api_key=%s", TMDB_API_BASE_URL, tvShowId, apiKey);
        return fetchFromApi(url, this::mapApiResponseToTvShow);
    }

    public Set<Episode> fetchSeasonEpisodes(int tvShowId, int seasonNumber) {
        String url = String.format("%s/tv/%d/season/%d?api_key=%s", TMDB_API_BASE_URL, tvShowId, seasonNumber, apiKey);
        return fetchFromApi(url, this::mapApiResponseToEpisodes);
    }

    public Object[] fetchFilmCredits(int filmId, int filmType) {
        String url = String.format("%s/%s/%d/credits?api_key=%s", TMDB_API_BASE_URL, filmType == 0 ? "movie" : "tv",
                filmId, apiKey);
        if (filmType == 0) {
            return fetchFromApi(url, this::mapApiResponseToMovieCredits);
        } else {
            return fetchFromApi(url, this::mapApiResponseToTvShowCredits);
        }
    }

    private Person mapApiResponseToPerson(JsonNode root) {
        Person person = personService.getPersonById(root.get("id").asInt());
        if (person == null) {
            person = new Person();

        }
        personService.addPerson(person);

        person.setId(root.get("id").asInt());
        person.setName(getValueAsText(root.get("name")));
        person.setBiography(getValueAsText(root.get("biography")));
        person.setGender(getValueAsInt(root.get("gender")));
        person.setAdult(getValueAsBoolean(root.get("adult")));
        person.setKnownFor(getValueAsText(root.get("known_for_department")));
        person.setBirthDate(getValueAsLocalDate(root.get("birthday")));
        person.setDeathDate(getValueAsLocalDate(root.get("deathday")));
        person.setPopularity(getValueAsDouble(root.get("popularity")));
        person.setProfilePath(getValueAsText(root.get("profile_path")));
        person.setBirthPlace(getValueAsText(root.get("place_of_birth")));
        person.setImdbId(getValueAsText(root.get("imdb_id")));
        person.setHomepage(getValueAsText(root.get("homepage")));

        List<String> also_known_as = StreamSupport.stream(root.get("also_known_as").spliterator(), false)
                .map(JsonNode::asText).collect(Collectors.toList());


        Object[] credits = fetchPersonCredits(person.getId());
        @SuppressWarnings("unchecked")
        Set<Cast> cast = (Set<Cast>) credits[0];
        @SuppressWarnings("unchecked")
        Set<Crew> crew = (Set<Crew>) credits[1];

        person.setAlsoKnownAs(also_known_as);
        person.setCast(cast);
        person.setCrew(crew);

        person.setLastUpdated(LocalDate.now());
        personService.addPerson(person);
       
        return person;
    }

    private Object[] mapApiResponseToPersonCredits(JsonNode root)
    {
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

                // Film logic
                int media_type = castNode.get("media_type").asText().equals("movie") ? 0 : 1;
                int filmId = castNode.get("id").asInt();
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
                film.setAdult(getValueAsBoolean(castNode.get("adult")));
                film.setBackdropPath(getValueAsText(castNode.get("backdrop_path")));
                film.setOriginalLanguage(getValueAsText(castNode.get("original_language")));
                film.setOriginalName(getValueAsText(castNode.get("original_title")));
                film.setOverview(getValueAsText(castNode.get("overview")));
                film.setPopularity(getValueAsDouble(castNode.get("popularity")));
                film.setPosterPath(getValueAsText(castNode.get("poster_path")));
                film.setTitle(getValueAsTitleOrName(castNode));
                film.setVoteAverage(getValueAsDouble(castNode.get("vote_average")));
                film.setVoteCount(getValueAsInt(castNode.get("vote_count")));


                // Cast logic
                CastId castId = new CastId(personId, filmIdObj);
                Cast castMember = personService.getCastById(castId);
                if (castMember == null) {
                    castMember = new Cast();
                }
                castMember.setId(castId);
                castMember.setCharacter(getValueAsText(castNode.get("character")));
                castMember.setOrdering(getValueAsInt(castNode.get("order")));
                castMember.setPersonName(person.getName());
                castMember.setProfilePath(person.getProfilePath());
                castMember.setFilmTitle(film.getTitle());
                castMember.setLastUpdated(LocalDate.now());
                castMember.setPerson(person);
                castMember.setFilm(film);
                personService.addCast(castMember);

                // Adding the cast member to the film object
                Set<Cast> played_in = film.getCast();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(castMember);
                film.setCast(played_in);
                if (media_type == 0) {
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
                int media_type = crewNode.get("media_type").asText().equals("movie") ? 0 : 1;
                int filmId = crewNode.get("id").asInt();
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
                film.setAdult(getValueAsBoolean(crewNode.get("adult")));
                film.setBackdropPath(getValueAsText(crewNode.get("backdrop_path")));
                film.setOriginalLanguage(getValueAsText(crewNode.get("original_language")));
                film.setOriginalName(getValueAsText(crewNode.get("original_title")));
                film.setOverview(getValueAsText(crewNode.get("overview")));
                film.setPopularity(getValueAsDouble(crewNode.get("popularity")));
                film.setPosterPath(getValueAsText(crewNode.get("poster_path")));
                film.setTitle(getValueAsTitleOrName(crewNode));
                film.setVoteAverage(getValueAsDouble(crewNode.get("vote_average")));
                film.setVoteCount(getValueAsInt(crewNode.get("vote_count")));

                String department = getValueAsText(crewNode.get("department"));
                String job = getValueAsText(crewNode.get("job"));
                if (department == null) {
                    department = "Unknown";
                }
                if (job == null) {
                    job = "Unknown";
                }

                // Crew logic 
                CrewId crewId = new CrewId(personId, filmIdObj, department, job);
                Crew crewMember = personService.getCrewById(crewId);
                if (crewMember == null) {
                    crewMember = new Crew();
                }
                crewMember.setId(crewId);
                crewMember.setPersonName(person.getName());
                crewMember.setProfilePath(person.getProfilePath());
                crewMember.setFilmTitle(film.getTitle());
                crewMember.setLastUpdated(LocalDate.now());
                crewMember.setPerson(person);
                crewMember.setFilm(film);
                personService.addCrew(crewMember);

                // Adding the crew member to the film object
                Set<Crew> played_in = film.getCrew();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(crewMember);
                film.setCrew(played_in);
                if (media_type == 0) {
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



    /**
     * Maps the JSON response from the API to a Film object.
     * This method is used by both Movie and TvShow mapping methods to map common
     * fields.
     * 
     * @param root JSON response from the API
     * @param film Film object to map the response to
     */
    private void mapApiResponseToFilm(JsonNode root, Film film) {
        String title = getValueAsTitleOrName(root);
        Boolean adult = getValueAsBoolean(root.get("adult"));
        String homepage = getValueAsText(root.get("homepage"));
        String backdropPath = getValueAsText(root.get("backdrop_path"));
        String posterPath = getValueAsText(root.get("poster_path"));
        String originalName = getValueAsText(root.get("original_title"));
        String originalLanguage = getValueAsText(root.get("original_language"));
        String overview = getValueAsText(root.get("overview"));
        Double popularity = getValueAsDouble(root.get("popularity"));
        String tagline = getValueAsText(root.get("tagline"));
        Integer voteCount = getValueAsInt(root.get("vote_count"));
        Double voteAverage = getValueAsDouble(root.get("vote_average"));

        List<String> genres = root.get("genres").findValues("name").stream().map(JsonNode::asText)
                .collect(Collectors.toList());
        List<String> productionCountries = root.get("production_countries").findValues("name").stream()
                .map(JsonNode::asText).collect(Collectors.toList());
        List<String> spokenLanguages = root.get("spoken_languages").findValues("english_name").stream()
                .map(JsonNode::asText).collect(Collectors.toList());
        List<String> originCountries = StreamSupport.stream(root.get("origin_country").spliterator(), false)
                .map(JsonNode::asText).collect(Collectors.toList());

        Object[] credits = fetchFilmCredits(film.getId().getFilmId(), film.getId().getFilmType());
        @SuppressWarnings("unchecked")
        Set<Cast> cast = (Set<Cast>) credits[0];
        @SuppressWarnings("unchecked")
        Set<Crew> crew = (Set<Crew>) credits[1];

        // Map production companies if available
        Set<ProductionCompany> produced_set = mapFilmProductionCompanies(root, film);

        film.setTitle(title);
        film.setAdult(adult);
        film.setHomepage(homepage);
        film.setBackdropPath(backdropPath);
        film.setPosterPath(posterPath);
        film.setOriginalName(originalName);
        film.setOriginalLanguage(originalLanguage);
        film.setOverview(overview);
        film.setPopularity(popularity);
        film.setTagline(tagline);
        film.setVoteCount(voteCount);
        film.setVoteAverage(voteAverage);

        film.setGenres(genres);
        film.setProductionCountries(productionCountries);
        film.setSpokenLanguages(spokenLanguages);
        film.setOriginCountries(originCountries);

        film.setCrew(crew);
        film.setCast(cast);
        film.setProduced(produced_set);
        film.setLastUpdated(LocalDate.now());
    }

    private Set<ProductionCompany> mapFilmProductionCompanies(JsonNode root, Film film) {
        if (!root.get("production_companies").isNull()) {

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
        return null; // Returns null if no production companies are found
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

        if (!root.get("cast").isNull()) {
            cast = new HashSet<>();
            for (JsonNode castNode : root.get("cast")) {

                // Person logic
                int personId = castNode.get("id").asInt();
                Person person = personService.getPersonById(personId);
                if (person == null) {
                    person = new Person();
                }
                person.setId(personId);
                person.setName(getValueAsText(castNode.get("name")));
                person.setGender(getValueAsInt(castNode.get("gender")));
                person.setAdult(getValueAsBoolean(castNode.get("adult")));
                person.setKnownFor(getValueAsText(castNode.get("known_for_department")));
                person.setPopularity(getValueAsDouble(castNode.get("popularity")));
                person.setProfilePath(getValueAsText(castNode.get("profile_path")));
                personService.addPerson(person);

                // Film logic
                FilmId filmIdObj = new FilmId(filmId, filmType); // Creates a new film id object
                // Finds film depending if it is a movie or a tv-show
                Film film = filmType == 0 ? movieService.getMovieById(filmId) : tvShowService.getTvShowById(filmId);

                // if it coudnt have been found, then creates a new one as the respective type
                if (film == null) {
                    film = filmType == 0 ? new Movie() : new TvShow();
                }
                film.setId(filmIdObj); // Sets the id
                if (filmType == 0) {
                    movieService.addMovie((Movie) film); // adds the respective film type to the database
                } else {
                    tvShowService.addTvShow((TvShow) film);
                }

                // Cast logic
                CastId castId = new CastId(personId, filmIdObj);
                Cast castMember = personService.getCastById(castId);
                if (castMember == null) {
                    castMember = new Cast();
                }
                castMember.setId(castId);
                castMember.setCharacter(getValueAsText(castNode.get("character")));
                castMember.setOrdering(getValueAsInt(castNode.get("order")));
                castMember.setPersonName(person.getName());
                castMember.setProfilePath(person.getProfilePath());
                castMember.setFilmTitle(film.getTitle());
                castMember.setLastUpdated(LocalDate.now());
                castMember.setPerson(person);
                castMember.setFilm(film);
                personService.addCast(castMember);

                // Adding the cast member to the person object
                Set<Cast> played_in = person.getCast();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(castMember);
                person.setCast(played_in);
                personService.addPerson(person);

                cast.add(castMember);
            }
        }

        if (!root.get("crew").isNull()) {
            crew = new HashSet<>();
            for (JsonNode crewNode : root.get("crew")) {

                // Person logic
                int personId = crewNode.get("id").asInt();
                Person person = personService.getPersonById(personId);
                if (person == null) {
                    person = new Person();
                }
                person.setId(personId);
                person.setName(getValueAsText(crewNode.get("name")));
                person.setGender(getValueAsInt(crewNode.get("gender")));
                person.setAdult(getValueAsBoolean(crewNode.get("adult")));
                person.setKnownFor(getValueAsText(crewNode.get("known_for_department")));
                person.setPopularity(getValueAsDouble(crewNode.get("popularity")));
                person.setProfilePath(getValueAsText(crewNode.get("profile_path")));
                personService.addPerson(person);

                // Film logic
                FilmId filmIdObj = new FilmId(filmId, filmType); // Creates a new film id object
                // Finds film depending if it is a movie or a tv-show
                Film film = filmType == 0 ? movieService.getMovieById(filmId) : tvShowService.getTvShowById(filmId);

                // if it coudnt have been found, then creates a new one as the respective type
                if (film == null) {
                    film = filmType == 0 ? new Movie() : new TvShow();
                }
                film.setId(filmIdObj); // Sets the id
                if (filmType == 0) {
                    movieService.addMovie((Movie) film); // adds the respective film type to the database
                } else {
                    tvShowService.addTvShow((TvShow) film);
                }

                // Crew logic
                String department = getValueAsText(crewNode.get("department"));
                String job = getValueAsText(crewNode.get("job"));
                if (department == null) {
                    department = "Unknown";
                }
                if (job == null) {
                    job = "Unknown";
                }

                // Crew logic 
                CrewId crewId = new CrewId(personId, filmIdObj, department, job);
                Crew crewMember = personService.getCrewById(crewId);
                if (crewMember == null) {
                    crewMember = new Crew();
                }
                crewMember.setId(crewId);
                crewMember.setPersonName(person.getName());
                crewMember.setProfilePath(person.getProfilePath());
                crewMember.setFilmTitle(film.getTitle());
                crewMember.setLastUpdated(LocalDate.now());
                crewMember.setPerson(person);
                crewMember.setFilm(film);
                personService.addCrew(crewMember);

                // Adding the crew member to the person object
                Set<Crew> played_in = person.getCrew();
                if (played_in == null)
                    played_in = new HashSet<>();
                played_in.add(crewMember);
                person.setCrew(played_in);
                personService.addPerson(person);

                crew.add(crewMember);
            }
        }
        Object[] credits = { cast, crew };
        return credits;
    }

    /**
     * Maps the JSON response from the API to a Movie object
     * 
     * @param root
     * @return Movie object
     */
    private Movie mapApiResponseToMovie(JsonNode root) {
        Movie movie = new Movie();

        FilmId filmId = new FilmId(root.get("id").asInt(), 0);
        movie.setId(filmId);
        movieService.addMovie(movie);
        mapApiResponseToFilm(root, movie);

        Long budget = getValueAsLong(root.get("budget"));
        LocalDate releaseDate = getValueAsLocalDate(root.get("release_date"));
        Long revenue = getValueAsLong(root.get("revenue"));
        Integer runtime = getValueAsInt(root.get("runtime"));

        // Map collection if available
        Collection collection = mapMovieCollection(root, movie);

        movie.setBudget(budget);
        movie.setReleaseDate(releaseDate);
        movie.setRevenue(revenue);
        movie.setRuntime(runtime);
        movie.setCollection(collection);
        movieService.addMovie(movie);

        return movie;
    }

    private Collection mapMovieCollection(JsonNode root, Movie movie) {
        if (!root.get("belongs_to_collection").isNull()) {
            Collection collection = new Collection();

            JsonNode collNode = root.get("belongs_to_collection");
            Integer colId = getValueAsInt(collNode.get("id"));
            collection = collectionService.getCollectionById(colId);
            if (collection == null) {
                collection = new Collection();
            }
            collection.setId(colId);
            collection.setName(getValueAsText(collNode.get("name")));
            collection.setPosterPath(getValueAsText(collNode.get("poster_path")));
            collection.setBackdropPath(getValueAsText(collNode.get("backdrop_path")));

            Set<Movie> movieList = collection.getMovies();
            if (movieList == null)
                movieList = new HashSet<>();
            movieList.add(movie);
            collection.setMovies(movieList);
            collectionService.addCollection(collection);

            return collection;
        }
        return null;
    }

    /**
     * Maps the JSON response from the API to a TvShow object
     * 
     * @param root
     * @return TvShow object
     */
    private TvShow mapApiResponseToTvShow(JsonNode root) {
        TvShow tvShow = new TvShow();

        FilmId filmId = new FilmId(root.get("id").asInt(), 1);
        tvShow.setId(filmId);
        tvShowService.addTvShow(tvShow);
        mapApiResponseToFilm(root, tvShow);

        LocalDate firstAirDate = getValueAsLocalDate(root.get("first_air_date"));
        LocalDate lastAirDate = getValueAsLocalDate(root.get("last_air_date"));
        Boolean inProduction = getValueAsBoolean(root.get("in_production"));
        Integer numberOfEpisodes = getValueAsInt(root.get("number_of_episodes"));
        Integer numberOfSeasons = getValueAsInt(root.get("number_of_seasons"));

        Set<Person> creators = mapTvShowCreators(root, tvShow);
        Set<Network> networks = mapTvShowNetworks(root, tvShow);
        Set<Season> seasons = mapTvShowSeasons(root, tvShow);

        tvShow.setFirstAirDate(firstAirDate);
        tvShow.setLastAirDate(lastAirDate);
        tvShow.setInProduction(inProduction);
        tvShow.setNumberOfEpisodes(numberOfEpisodes);
        tvShow.setNumberOfSeasons(numberOfSeasons);
        tvShow.setCreators(creators);
        tvShow.setNetworks(networks);
        tvShow.setSeasons(seasons);
        tvShowService.addTvShow(tvShow);

        return tvShow;
    }

    private Set<Person> mapTvShowCreators(JsonNode root, TvShow tvShow) {
        if (!root.get("created_by").isNull()) {
            Set<Person> creators = new HashSet<>();
            for (JsonNode creator : root.get("created_by")) {
                int creatorId = creator.get("id").asInt();
                Person person = personService.getPersonById(creatorId);
                if (person == null) {
                    person = new Person();
                }
                person.setId(creatorId);
                person.setName(creator.get("name").asText());
                person.setProfilePath(creator.get("profile_path").asText());

                Set<TvShow> createdShows = person.getCreatedTvShows();
                if (createdShows == null)
                    createdShows = new HashSet<>();
                createdShows.add(tvShow);
                person.setCreatedTvShows(createdShows);

                personService.addPerson(person);
                creators.add(person);
            }
            return creators;
        }
        return null;
    }

    private Set<Network> mapTvShowNetworks(JsonNode root, TvShow tvShow) {
        if (!root.get("networks").isNull()) {
            Set<Network> networks = new HashSet<>();
            for (JsonNode network : root.get("networks")) {
                int networkId = network.get("id").asInt();
                Network networkObj = networkService.getNetworkById(networkId);
                if (networkObj == null) {
                    networkObj = new Network();
                }
                networkObj.setId(networkId);
                networkObj.setName(network.get("name").asText());
                networkObj.setLogoPath(network.get("logo_path").asText());
                networkObj.setOriginCountry(network.get("origin_country").asText());

                Set<TvShow> tvShows = networkObj.getTvShows();
                if (tvShows == null)
                    tvShows = new HashSet<>();
                tvShows.add(tvShow);
                networkObj.setTvShows(tvShows);

                networkService.addNetwork(networkObj);
                networks.add(networkObj);
            }
            return networks;
        }
        return null;
    }

    private Set<Season> mapTvShowSeasons(JsonNode root, TvShow tvShow) {
        if (!root.get("seasons").isNull()) {
            Set<Season> seasons = new HashSet<>();
            for (JsonNode season : root.get("seasons")) {
                int seasonId = season.get("id").asInt();
                Season seasonObj = tvShowService.getSeasonById(seasonId);
                if (seasonObj == null) {
                    seasonObj = new Season();
                }
                seasonObj.setId(seasonId);
                seasonObj.setAirDate(getValueAsLocalDate(season.get("air_date")));
                seasonObj.setName(getValueAsText(season.get("name")));
                seasonObj.setOverview(getValueAsText(season.get("overview")));
                seasonObj.setEpisodeCount(getValueAsInt(season.get("episode_count")));
                seasonObj.setPosterPath(getValueAsText(season.get("poster_path")));
                seasonObj.setSeasonNumber(getValueAsInt(season.get("season_number")));
                seasonObj.setVoteAverage(getValueAsDouble(season.get("vote_average")));

                seasonObj.setTvShow(tvShow);

                Set<Season> tv_show_seasons = tvShow.getSeasons();
                if (tv_show_seasons == null)
                    tv_show_seasons = new HashSet<>();
                tv_show_seasons.add(seasonObj);
                tvShow.setSeasons(tv_show_seasons);

                tvShowService.addSeason(seasonObj);
                Set<Episode> episodes = fetchSeasonEpisodes(tvShow.getId().getFilmId(), seasonObj.getSeasonNumber());
                seasonObj.setEpisodes(episodes);

                seasons.add(seasonObj);
            }
            return seasons;
        }
        return null;
    }

    private Set<Episode> mapApiResponseToEpisodes(JsonNode root) {
        Integer seasonId = getValueAsInt(root.get("id"));

        if (!root.get("episodes").isNull()) {
            Set<Episode> episodes = new HashSet<>();

            for (JsonNode episode : root.get("episodes")) {
                int episodeId = episode.get("id").asInt();

                Episode episodeObj = tvShowService.getEpisodeById(episodeId);
                if (episodeObj == null) {
                    episodeObj = new Episode();
                }
                episodeObj.setId(episodeId);
                episodeObj.setAirDate(getValueAsLocalDate(episode.get("air_date")));
                episodeObj.setEpisodeNumber(getValueAsInt(episode.get("episode_number")));
                episodeObj.setEpisodeType(getValueAsText(episode.get("type")));
                episodeObj.setName(getValueAsText(episode.get("name")));
                episodeObj.setOverview(getValueAsText(episode.get("overview")));
                episodeObj.setRuntime(getValueAsInt(episode.get("runtime")));
                episodeObj.setStillPath(getValueAsText(episode.get("still_path")));
                episodeObj.setVoteAverage(getValueAsDouble(episode.get("vote_average")));
                episodeObj.setVoteCount(getValueAsInt(episode.get("vote_count")));

                Season season = tvShowService.getSeasonById(seasonId);
                if (season == null) {
                    season = new Season();
                    season.setId(seasonId);
                }

                episodeObj.setSeason(season);
                tvShowService.addEpisode(episodeObj);

                episodes.add(episodeObj);
            }
            return episodes;
        }
        return null;
    }

    // Helper methods to extract values from JSON nodes
    private String getValueAsTitleOrName(JsonNode node) {
        if (node.hasNonNull("title")) {
            return getValueAsText(node.get("title"));
        }
        return getValueAsText(node.get("name"));
    }

    private String getValueAsText(JsonNode node) {
        return node != null && !node.isNull() ? node.asText() : null;
    }

    private Integer getValueAsInt(JsonNode node) {
        return node != null && !node.isNull() ? node.asInt() : null;
    }

    private Long getValueAsLong(JsonNode node) {
        return node != null && !node.isNull() ? node.asLong() : null;
    }

    private Double getValueAsDouble(JsonNode node) {
        return node != null && !node.isNull() ? node.asDouble() : null;
    }

    private Boolean getValueAsBoolean(JsonNode node) {
        return node != null && !node.isNull() ? node.asBoolean() : null;
    }

    private LocalDate getValueAsLocalDate(JsonNode node) {

        if (node != null && !node.isNull()) {
            String date = node.asText();
            if (date.equals(""))
                return null;
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        }
        return null;

    }
}
