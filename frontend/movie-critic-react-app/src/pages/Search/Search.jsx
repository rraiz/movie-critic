import React from 'react';
import { useLocation } from 'react-router-dom';

export default function Search() {

    // Function to get query parameter value by name
    const getQueryParam = (param) => {
        return new URLSearchParams(useLocation().search).get(param);
    };

    // Extract the 'query' parameter
    const query = getQueryParam('query');

    return (
        <div className="bg-[#14181d] text-[#FFFFFF] text-center min-h-screen">
            <h1 className='text-[25px] font-bold'>{query ? `Search "${query}"` : 'No query provided'}</h1>
        </div>
    );
}
