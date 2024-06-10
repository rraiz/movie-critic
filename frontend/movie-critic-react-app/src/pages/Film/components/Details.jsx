export default function Details({ film, type }) {
    return (
        <div className='mt-[40px] ml-5'>
            <h2 className="font-bold text-[24px] mb-2">Details</h2>
            <div className="">
                <div className="flex text-gray-300 space-x-10">
                    <div className='flex-1 min-w-[315px] flex flex-col space-y-2'>
                        {film?.homepage && (
                            <p><strong><a href={film.homepage} target="_blank" rel="noopener noreferrer" className="hover:underline">Homepage</a></strong></p>
                        )}
                        {film?.originCountries && (
                            <div>
                                <p><strong>Origin Countries</strong></p>
                                <p>{film.originCountries.join(', ')}</p>
                            </div>
                        )}
                        {film?.originalLanguage && (
                            <div>
                                <p><strong>Original Language</strong></p>
                                <p>{film.originalLanguage}</p>
                            </div>
                        )}
                        {film?.spokenLanguages && (
                            <div>
                                <p><strong>Spoken Languages</strong></p>
                                <p>{film.spokenLanguages.join(', ')}</p>
                            </div>
                        )}
                    </div>

                    <div className='flex-1 min-w-[415px] flex flex-col space-y-2'>
                        {type === 'tv' && film?.networks && (
                            <div>
                                <p><strong>Networks</strong></p>
                                <p>{film.networks.map(network => network.name).join(', ')}</p>
                            </div>
                        )}
                        {type === 'movie' && film?.collection && (
                            <div>
                                <p><strong>Collection</strong></p>
                                <p>{film.collection.name}</p>
                            </div>
                        )}
                        {film?.productionCountries && (
                            <div>
                                <p><strong>Production Countries</strong></p>
                                <p>{film.productionCountries.join(', ')}</p>
                            </div>
                        )}
                        {film?.produced && (
                            <div>
                                <p><strong>Production Companies</strong></p>
                                <p>{film.produced.map(company => company.name).join(', ')}</p>
                            </div>
                        )}
                    </div>

                    <div className='flex-1 min-w-[215px] flex flex-col space-y-2'>
                        {type === 'tv' && (
                            <>
                                {film?.numberOfSeasons && (
                                    <div>
                                        <p><strong>Seasons</strong></p>
                                        <p>{film.numberOfSeasons}</p>
                                    </div>
                                )}
                                {film?.numberOfEpisodes && (
                                    <div>
                                        <p><strong>Episodes</strong></p>
                                        <p>{film.numberOfEpisodes}</p>
                                    </div>
                                )}
                                {film?.firstAirDate && (
                                    <div>
                                        <p><strong>First Aired</strong></p>
                                        <p>{film.firstAirDate}</p>
                                    </div>
                                )}
                                {film?.lastAirDate && (
                                    <div>
                                        <p><strong>Last Aired</strong></p>
                                        <p>{film.lastAirDate}</p>
                                    </div>
                                )}
                                {film?.inProduction !== undefined && (
                                    <div>
                                        <p><strong>Status</strong></p>
                                        <p>{film.inProduction ? "in production" : "Ended"}</p>
                                    </div>
                                )}
                            </>
                        )}
                        {type === 'movie' && (
                            <>
                                {film?.runtime && (
                                    <div>
                                        <p><strong>Runtime</strong></p>
                                        <p>{film.runtime} minutes</p>
                                    </div>
                                )}
                                {film?.releaseDate && (
                                    <div>
                                        <p><strong>Release Date</strong></p>
                                        <p>{film.releaseDate}</p>
                                    </div>
                                )}
                                {film?.revenue && (
                                    <div>
                                        <p><strong>Revenue</strong></p>
                                        <p>${film.revenue.toLocaleString()}</p>
                                    </div>
                                )}
                                {film?.budget && (
                                    <div>
                                        <p><strong>Budget</strong></p>
                                        <p>${film.budget.toLocaleString()}</p>
                                    </div>
                                )}
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
