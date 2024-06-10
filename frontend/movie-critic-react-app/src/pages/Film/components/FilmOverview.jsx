import altPoster from '../../../../public/alt-poster.png';
import { FaStar } from 'react-icons/fa';

export default function FilmOverview({ film, type }) {

    let firstAirYear;
    let lastAirYear;
    let creators;
    if (type === 'tv') {

        // Extract the first and last air years from the dates
        firstAirYear = new Date(film.firstAirDate).getFullYear();
        lastAirYear = new Date(film.lastAirDate).getFullYear();
        
        // If the film is still in production, set last air year to 'Present'
        if (film.inProduction) {
            lastAirYear = 'Present';
        }

        // Get the names of the creators as a comma-separated string
        if (film.creators) {
            creators = film.creators.map(creator => creator.name).join(', ');
        } 

    }

    let releaseYear;
    let directors;
    if (type === 'movie') {
        releaseYear = new Date(film.releaseDate).getFullYear();

        // Get the names of the directors as a comma-separated string
        if (film.crew) {
            directors = film.crew.filter(member => member.job === 'Director').map(member => member.personName).join(', ');
        }
    }

    return (
        <div className='grid grid-cols-3 -mt-10'>
            
            {/* Poster Image Section */}
            <img
                src={film.posterPath ? `https://image.tmdb.org/t/p/original/${film.posterPath}` : altPoster}
                className="max-w-[250px] mr-auto col-span-1 rounded-md cover-bg border border-black z-10"
                alt="Poster Image"
            />

            <div className='col-span-2 z-10'>

                {/* Header Section: Title, Air Years, Creators */}
                <div className='flex items-baseline flex-wrap gap-x-4'>
                    <h1 className="font-bold text-[40px]">{film.title}</h1>
                    {type == "tv" && <p className='text-[#d7dae1] '>{firstAirYear} - {lastAirYear}</p>}
                    {creators && <p className='text-[#d7dae1]'>Created by {creators}</p>}

                    {type == "movie" && <p className='text-[#d7dae1] '>{releaseYear}</p>}
                    {directors && <p className='text-[#d7dae1]'>Directed by {directors}</p>}
                </div>
                <div className="flex flex-col">

                    {/* Tagline */}
                    <p className='font-bold mb-2 mt-3'>{film.tagline}</p>

                    {/* Overview */}
                    <p className="whitespace-pre-wrap mb-5">{film.overview}</p>

                    {/* Genres Section */}
                    <div className="flex flex-wrap mb-2 mt-4">
                        {film.genres.map((genre, index) => (
                            <div key={index} className="bg-[#2b2f335d] small-shadow text-[#eef1f8] px-3 py-1 rounded-full text-[15px] mr-2 mb-2 inline-block">
                                {genre}
                            </div>
                        ))}
                    </div>

                    {/* Rating Section */}
                    <div className="flex items-center text-[#d7dae1] text-lg">
                        <FaStar className="text-yellow-500 mr-1" />
                        <span className="font-semibold mr-2">{film.voteAverage}</span>
                        <span className="text-sm">(TMDB Rating - {film.voteCount} votes)</span>
                    </div>
                </div>
            </div>
        </div>
    );
}
