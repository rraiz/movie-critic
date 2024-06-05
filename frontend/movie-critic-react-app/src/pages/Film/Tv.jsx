import "./film.css";
import altPoster from '../../../public/alt-poster.png';

export default function Tv({film}) {

    const firstAirYear = new Date(film.firstAirDate).getFullYear();
    let lastAirYear = new Date(film.lastAirDate).getFullYear();

    if (film.inProduction) {
        lastAirYear = 'Present';
    } 

    const creators = film.creators.map(creator => creator.name).join(', ');

    return (
        <div className='grid grid-cols-3 -mt-10'>
        <img 
            src={film.posterPath ? `https://image.tmdb.org/t/p/original/${film.posterPath}` : altPoster} className="max-w-[250px] mr-auto col-span-1 rounded-md cover-bg border border-black z-10" 
            alt="Poster Image" 
        />
        <div className='col-span-2'>
            <div className='flex items-baseline flex-wrap'>
                <h1 className="font-bold text-[40px] text-[#FFFFFF] z-10">{film.title}</h1>
                <p className='text-[#d7dae1] ml-4 mr-4'>{firstAirYear} - {lastAirYear}</p>
                {creators && <p className='text-[#d7dae1]'>Created by {creators}</p>}            </div>
            <p className='font-bold mb-2 mt-3'>{film.tagline}</p>
            <p className="whitespace-pre-wrap">{film.overview}</p>
        </div>
    </div>
    )

}
