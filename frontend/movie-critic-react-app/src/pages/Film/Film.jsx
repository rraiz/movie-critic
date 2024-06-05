import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import "./film.css";

import Error from '../Error/Error';

import Movie from './Movie';
import Tv from './Tv';
import Backdrop from './components/Backdrop';

export default function Film({type}) {

    const { id } = useParams();
    const [error, setError] = useState(false);
    const [isLoading, setIsLoading] = useState(false); 
    const [film, setFilm] = useState(null);

    useEffect(() => {
        const fetchFilm = async () => {
            setIsLoading(true);
            try {
                const response = await fetch(`http://localhost:8080/api/v1/${type}/${id}`);
                const data = await response.json();
                setFilm(data); // Assuming the response has a title property
            } catch (e) {
                setError(true);
            } finally {
                setIsLoading(false);
            }
        };

        fetchFilm();
    }, []);

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
            <div className="text-[#FFFFFF] min-h-screen max-w-[950px] mx-auto">
                <main>
                    <Backdrop backdropPath={film.backdropPath} />
                    {type === 'movie' ? <Movie film={film} /> : <Tv film={film} />}
                </main>
            </div>
        </div>
    );
}
