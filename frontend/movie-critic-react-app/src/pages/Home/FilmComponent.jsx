export default function FilmComponent({ film }) {
  return (
    <div className="max-w-[150px] min-w-[150px] mr-[20px]">
      <div className="relative w-full h-[225px]">
        <img 
          src={`https://image.tmdb.org/t/p/w200${film.posterPath}`} 
          alt={film.title} 
          className="absolute inset-0 w-full h-full object-cover rounded-lg"
        />
      </div>
      <h2 className="text-white mt-2">{film.title}</h2>
      <p className="text-gray-400">{film.releaseDate}</p>
    </div>
  );
};
