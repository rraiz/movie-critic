import { useLocation } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';

import Error from '../Error/Error';
import Card from './components/Card.jsx';

export default function Search() {

    const query = new URLSearchParams(useLocation().search).get("query");

    const {data: film, isLoading, error} = useQuery({
        queryFn: () => fetchFilms(query),
        queryKey: ['films', {query}],
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
        );
    }

    if (error) {
        return <Error />
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

const fetchFilms = async (query) => {
    const searchQuery = query ? query.replace(/\s/g, '%20') : '';
    if (!searchQuery) return [];
    const response = await fetch(`http://localhost:8080/api/v1/search/film/${searchQuery}`);
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
};
