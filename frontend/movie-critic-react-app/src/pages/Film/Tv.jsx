import FilmOverview from './components/FilmOverview.jsx';
import CastCards from './components/CastCards.jsx';
import Details from './components/Details.jsx';

export default function Tv({ film }) {

    return (
        <>
            <FilmOverview film={film} type="tv" />
            <CastCards members={film.cast} type="tv" />
            <Details film={film} type="tv" />
        </>
    );
}
