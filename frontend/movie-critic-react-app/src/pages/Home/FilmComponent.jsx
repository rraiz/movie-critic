export default function FilmComponent({ film, type }) {
  return (
    <div className="max-w-[150px] min-w-[150px] mr-[20px]">
      <div className="relative w-full h-[225px]">
        <a href={`/${type}/${film.id}`}>
          <img 
            src={`https://image.tmdb.org/t/p/w200${film.posterPath}`} 
            alt={film.title} 
            className="absolute inset-0 w-full h-full object-cover rounded-lg transition-all duration-[50ms] hover:brightness-[.85]"
          />
        </a>
      </div>
      <h2 className="text-white mt-2">
        <a href={`/${type}/${film.id}`} className="hover:text-gray-300">
          {film.title}
        </a>
      </h2>
      <p className="text-gray-400">{film.releaseDate}</p>
    </div>
  );
};
