import React, { useState, useEffect } from 'react';
import WriteReview from './components/WriteReview';
import { FaStar, FaStarHalf } from 'react-icons/fa';
import { useIsLoggedIn } from '../../components/useSessionCookies';

export default function UserReviews() {
    const isLoggedIn = useIsLoggedIn();
    const [reviews, setReviews] = useState([]);
    const [averageRating, setAverageRating] = useState(0);

    useEffect(() => {
        if (reviews.length > 0) {
            const totalRating = reviews.reduce((acc, review) => acc + review.rating, 0);
            const avgRating = totalRating / reviews.length;
            setAverageRating(avgRating);
        } else {
            setAverageRating(0);
        }
    }, [reviews]);

    const addReview = (newReview) => {
        setReviews([newReview, ...reviews]);
    };

    const renderStars = (rating) => {
        const stars = [];
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars.push(<FaStar key={i} className="text-yellow-500 w-4 h-4" />);
            } else if (i === Math.ceil(rating) && !Number.isInteger(rating)) {
                stars.push(
                    <div key={i} className="relative w-4 h-4">
                        <FaStar className="text-gray-500 w-4 h-4 absolute top-0 left-0" />
                        <FaStarHalf className="text-yellow-500 w-4 h-4 absolute top-0 left-0" />
                    </div>
                );
            } else {
                stars.push(<FaStar key={i} className="text-gray-500 w-4 h-4" />);
            }
        }
        return stars;
    };

    return (
        <div className="mt-8 ml-4">
            <h2 className="text-2xl font-bold mb-4 text-[#fddb06]">User Reviews</h2>
            <div className="flex items-center pb-4">
                <div className="flex">
                    {renderStars(Math.round(averageRating * 2) / 2)}
                </div>
                <span className="ml-2">Based on {reviews.length} reviews</span>
            </div>
            
            {isLoggedIn() ? <WriteReview addReview={addReview} /> : null}
            
            {reviews.map((review, index) => (
                <div key={index} className="border-b border-gray-700 py-6 pb-10">
                    <div className="flex items-center pb-5">
                        <img src={`https://i.pravatar.cc/50?img=${index + 1}`} alt={review.name} className="w-10 h-10 rounded-full mr-3" />
                        <div>
                            <p className="font-bold">{review.name}</p>
                            <div className="flex">
                                {renderStars(review.rating)}
                            </div>
                        </div>
                    </div>
                    <p>{review.review}</p>
                </div>
            ))}
        </div>
    );
};