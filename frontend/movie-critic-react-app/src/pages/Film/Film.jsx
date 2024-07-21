import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';

import "./film.css";

import Error from '../Error/Error';

import Movie from './Movie';
import Tv from './Tv';
import Backdrop from './components/Backdrop';
import UserReviews from './UserReviews';

export default function Film({type}) {

    const { id } = useParams();
    const {data: film, isLoading, error} = useQuery({
        queryFn: () => fetchFilm(type, id),
        queryKey: ["film", {type, id}],
    });

    if (isLoading) {
        return (
            <div className='bg-[#14181d]'>
            <div className="text-[#FFFFFF] min-h-screen max-w-[950px] mx-auto">
                <main>
                  <h1>Loading...</h1>  
                </main>
            </div>
            </div>
        )
    }

    if (error || !film) {
        return <Error />;
    }

    return (
        <div className='bg-[#14181d]'>
            <div className="text-[#FFFFFF] min-h-screen max-w-[1000px] mx-auto">
                <main>
                    <Backdrop backdropPath={film.backdropPath} />
                    {type === 'movie' ? <Movie film={film} /> : <Tv film={film} />}
                    <UserReviews type={type} id={film.id}/>
                </main>
            </div>
        </div>
    );
}

const fetchFilm = async (type, id) => {
    const response = await fetch(`http://localhost:8080/api/v1/${type}/${id}`);
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
};