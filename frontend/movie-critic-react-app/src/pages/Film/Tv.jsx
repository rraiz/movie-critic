import "./film.css";
import altPoster from '../../../public/alt-poster.png';

export default function Tv({film}) {

    return (
        <div className='grid grid-cols-3 -mt-10'>
        <img 
            src={film.posterPath ? `https://image.tmdb.org/t/p/original/${film.posterPath}` : altPoster} className="max-w-[250px] mr-auto col-span-1 rounded-md cover-bg border border-black z-10" 
            alt="Poster Image" 
        />
        <div className='col-span-2'>
            <div className='flex items-baseline'>
                <h1 className="font-bold text-[50px] text-[#FFFFFF] drop-shadow-md">{film.title}</h1>
                <p className='text-[#d7dae1] ml-2'>{film.firstAirDate} - {film.lastAirDate}</p>
            </div>
            <p className='font-bold mb-2'>{film.tagline}</p>
            <p className="whitespace-pre-wrap">{film.overview}</p>
        </div>
    </div>
    )

}
