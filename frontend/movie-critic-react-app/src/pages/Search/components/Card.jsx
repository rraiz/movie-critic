import React from 'react';
import "../search.css";

export default function Card({ film }) {
    const poster = `https://image.tmdb.org/t/p/original/${film.posterPath}`;

    const id = film.id.filmType;
    const num = film.id.filmId;
    let firstAirYear;
    let type;
    if (id === 1) {
        firstAirYear = new Date(film.firstAirDate).getFullYear();
        type = 'tv';
    } else {
        firstAirYear = new Date(film.releaseDate).getFullYear();
        type = 'movie';
    }

    return (
        <div className="h-[190px] flex p-2">
            <img className="rounded-sm h-[150px] cover-bg" src={poster} alt={`${film.title} poster`} />

            <div className="pl-6 justify-between text-left w-full"> {/* Added w-full here */}
                <div className="flex items-baseline">
                    <a href={`/${type}/${num}`} className="text-[22px] font-bold hover:text-gray-300">
                        {film.title}
                    </a>
                    <span className="text-[22px] text-gray-400 ml-3 italic font-medium">{firstAirYear}</span>
                </div>

                <p className="pt-2 text-[15px] text-gray-400 overflow-hidden text-ellipsis line-clamp">
                    {film.overview}
                </p>

                {/* Genres Section */}
                <div className="flex flex-wrap mb-2 mt-4">
                    {film.genres.map((genre, index) => (
                        <div key={index} className="bg-[#6b6b6e5e] font-[501] text-gray-100 px-[13px] py-[4px] rounded-full text-[13px] mr-3 mb-2 inline-block">
                            {genre}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
