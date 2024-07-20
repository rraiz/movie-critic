import FilmComponent from './FilmComponent';

export default function FilmList({ title, films }) {
  return (
    <div className="pb-[75px]">
      <h1 className="text-white text-xl mb-4 font-medium">{title}</h1>
      <div className="flex overflow-x-scroll">
        {films.map((film) => (
          <FilmComponent key={film.id} film={film} />
        ))}
      </div>
    </div>
  );
};
