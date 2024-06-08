import altPoster from '../../../public/alt-poster.png';
import { FaStar } from 'react-icons/fa';
import ScrollCard from './components/ScrollCard';

export default function Tv({ film }) {
    const firstAirYear = new Date(film.firstAirDate).getFullYear();
    let lastAirYear = new Date(film.lastAirDate).getFullYear();

    if (film.inProduction) {
        lastAirYear = 'Present';
    }

    const creators = film.creators.map(creator => creator.name).join(', ');
    let genres = film.genres;

    return (
        <>
        <div className='grid grid-cols-3 -mt-10'>
            <img 
                src={film.posterPath ? `https://image.tmdb.org/t/p/original/${film.posterPath}` : altPoster} 
                className="max-w-[250px] mr-auto col-span-1 rounded-md cover-bg border border-black z-10" 
                alt="Poster Image" 
            />

            <div className='col-span-2 z-10'>
                <div className='flex items-baseline flex-wrap gap-x-4'>
                    <h1 className="font-bold text-[40px]">{film.title}</h1>
                    <p className='text-[#d7dae1] '>{firstAirYear} - {lastAirYear}</p>
                    {creators && <p className='text-[#d7dae1]'>Created by {creators}</p>}
                </div>
                <div className="flex flex-col">
                    <p className='font-bold mb-2 mt-3'>{film.tagline}</p>
                    <p className="whitespace-pre-wrap mb-5">{film.overview}</p>
                    <div className="flex flex-wrap mb-2 mt-4">
                        {genres.map((genre, index) => (
                            <div key={index} className="bg-[#2b2f335d] small-shadow text-[#eef1f8] px-3 py-1 rounded-full text-[15px] mr-2 mb-2 inline-block">
                                {genre}
                            </div>
                        ))}
                    </div>
                    <div className="flex items-center text-[#d7dae1] text-lg">
                        <FaStar className="text-yellow-500 mr-1" />
                        <span className="font-semibold mr-2">{film.voteAverage}</span>
                        <span className="text-sm">(TMDB Rating - {film.voteCount} votes)</span>
                    </div>
                </div>
            </div>
        </div>

            <ScrollCard members={film.cast} type="cast" />

            <div className='mt-[80px] ml-5'>
                <h2 className="font-bold text-[24px] mb-2">Details</h2>
                <div className="">
                    <div className="flex flex-none text-gray-300 space-x-10">
                        <div className='w-[315px]'>
                            <p className="mb-2"><strong><a href={film.homepage} target="_blank" rel="noopener noreferrer" className="text-gray-400 hover:underline">Homepage</a></strong></p>
                            <p className="mb-2"><strong>Origin Countries: </strong> {film.originCountries.join(', ')}</p>
                            <p className="mb-2"><strong>Original Language:</strong> {film.originalLanguage}</p>
                            <p className="mb-2"><strong>Spoken Languages: </strong> {film.spokenLanguages.join(', ')}</p>
                        </div>
                        <div className='w-[415px]'>
                            <p className="mb-2"><strong>Networks:</strong> {film.networks.map(network => network.name).join(', ')}</p>                       
                            <p className="mb-2"><strong>Production Countries: </strong> {film.productionCountries.join(', ')}</p>
                            <p className="mb-2"><strong>Production Companies:</strong> {film.produced.map(company => company.name).join(', ')}</p>
                        </div>
                        <div className='w-[215px]'>
                            <p className="mb-2"><strong>Seasons: </strong>{film.numberOfSeasons}</p>
                            <p className="mb-2"><strong>Episodes: </strong> {film.numberOfEpisodes}</p>
                            <p className="mb-2"><strong>First Aired: </strong> {film.firstAirDate}</p>
                            <p className="mb-2"><strong>Last Aired: </strong> {film.lastAirDate}</p>
                            <p className="mb-2"><strong>Status: </strong> {film.inProduction ? "in production" : "Ended"}</p>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
