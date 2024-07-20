import FilmComponent from './FilmComponent';

export default function FilmList({ title, films, type }) {
  // Sort films by popularity in descending order
  const sortedFilms = films.slice().sort((a, b) => b.popularity - a.popularity);

  return (
    <div className="pb-[75px]">
      <h1 className="text-white text-xl mb-4 font-medium">{title}</h1>
      <div className="flex overflow-x-scroll">
        {sortedFilms.map((film) => (
          <FilmComponent key={film.id} film={film} type={type} />
        ))}
      </div>
    </div>
  );
}
