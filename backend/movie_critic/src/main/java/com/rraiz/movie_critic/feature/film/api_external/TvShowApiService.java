package com.rraiz.movie_critic.feature.film.api_external;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rraiz.movie_critic.feature.film.model.entity.Network;
import com.rraiz.movie_critic.feature.film.model.entity.Person;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.feature.film.service.NetworkService;
import com.rraiz.movie_critic.feature.film.service.PersonService;
import com.rraiz.movie_critic.feature.film.service.TvShowService;
import com.rraiz.movie_critic.util.ApiUtil;

@Service
public class TvShowApiService {

    private final ApiUtil apiUtil;
    private final FilmApiService filmApiService;
    private final PersonService personService;
    private final TvShowService tvShowService;
    private final NetworkService networkService;

    public TvShowApiService(ApiUtil apiUtil, FilmApiService filmApiService, PersonService personService,
            TvShowService tvShowService, NetworkService networkService) {
        this.apiUtil = apiUtil;
        this.filmApiService = filmApiService;
        this.personService = personService;
        this.tvShowService = tvShowService;
        this.networkService = networkService;
    }

    public TvShow fetchTvShow(int tvShowId) {
        TvShow tvShow = fetchTvShowDetails(tvShowId);
        return tvShow;
    }

    public TvShow fetchTvShowDetails(int tvShowId) {
        String endpoint = "/tv/%d".formatted(tvShowId);
        return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToTvShow);
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
        filmApiService.mapFilm(root, tvShow);

        Set<Person> creators = mapTvShowCreators(root, tvShow);
        Set<Network> networks = mapTvShowNetworks(root, tvShow);

        tvShow.setFirstAirDate(apiUtil.getValueAsLocalDate(root.get("first_air_date")));
        tvShow.setLastAirDate(apiUtil.getValueAsLocalDate(root.get("last_air_date")));
        tvShow.setInProduction(apiUtil.getValueAsBoolean(root.get("in_production")));
        tvShow.setNumberOfEpisodes(apiUtil.getValueAsInt(root.get("number_of_episodes")));
        tvShow.setNumberOfSeasons(apiUtil.getValueAsInt(root.get("number_of_seasons")));
        tvShow.setCreators(creators);
        tvShow.setNetworks(networks);
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

        if (root.get("networks").isNull()) {
            return null;
        }

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

}
