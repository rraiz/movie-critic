import "./film.css";
import FilmOverview from "./components/FilmOverview";
import CastCards from "./components/CastCards";
import Details from "./components/Details";

export default function Movie({film}) {

    return (
    <>
            <FilmOverview film={film} type="movie"/>
            <CastCards members={film.cast} type="movie" />
            <Details film={film} type="movie" />
    </>
    )

}