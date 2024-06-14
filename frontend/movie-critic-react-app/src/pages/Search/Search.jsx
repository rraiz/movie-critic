import { useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';

import Error from '../Error/Error';
import Card from './components/Card.jsx';

export default function Search() {
    const query = new URLSearchParams(useLocation().search).get("query");
    const searchQuery = query ? query.replace(/\s/g, '%20') : '';

    const [error, setError] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [film, setFilm] = useState([]);

    useEffect(() => {
        const fetchFilm = async () => {
            if (!searchQuery) return;

            setIsLoading(true);
            try {
                const response = await fetch(`http://localhost:8080/api/v1/search/film/${searchQuery}`);
                const data = await response.json();
                setFilm(data);
            } catch (e) {
                setError(true);
            } finally {
                setIsLoading(false);
            }
        };

        fetchFilm();
    }, [searchQuery]);

    if (isLoading) {
        return (
            <div className='bg-[#14181d]'>
                <div className="text-[#FFFFFF] min-h-screen max-w-[950px] mx-auto">
                    <main>
                        <h1>Loading...</h1>
                    </main>
                </div>
            </div>
        );
    }

    if (error) {
        return <Error />;
    }

    return (
        <div className='bg-[#14181d]'>
            <div className="text-[#FFFFFF] text-center min-h-screen max-w-[950px] mx-auto">
                <h1 className='text-[25px] font-bold'>{query ? `Search "${query}"` : 'No query provided'}</h1>
                <div className="flex justify-center pt-2 pb-4">
                    <div className="border-t border-[#384148] w-[100%]"></div>
                </div>
                {film.length > 0 ? (
                    film.map((f, index) => (
                        <div key={index}>
                            <Card film={f} />
                            <div className="flex justify-center pb-[20px]">
                                <div className="border-t border-[#384148] w-[100%]"></div>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No results found</p>
                )}
            </div> 
        </div>
    );
}
