import { useEffect, useState } from 'react';
import { useQuery, useQueryClient, QueryClient, QueryClientProvider } from '@tanstack/react-query';
import WriteReview from './components/WriteReview';
import { FaStar, FaStarHalf } from 'react-icons/fa';
import { useIsLoggedIn } from '../../components/useSessionCookies';

// Create a client
const queryClient = new QueryClient();

export default function UserReviews({type, id}) {
    const isLoggedIn = useIsLoggedIn();
    const queryClient = useQueryClient();
    const [averageRating, setAverageRating] = useState(0);

    const film_type = type === 'movie' ? 0 : 1;

    const { data: reviews = [], isLoading, error } = useQuery({
        queryKey: ['reviews'],
        queryFn: async () => {
            const response = await fetch(`http://localhost:8080/api/v1/review/${film_type}/${id}/reviews`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        }
    });

    useEffect(() => {
        if (reviews.length > 0) {
            const totalRating = reviews.reduce((acc, review) => acc + review.rating, 0);
            const avgRating = totalRating / reviews.length;
            setAverageRating(avgRating);
        } else {
            setAverageRating(0);
        }
    }, [reviews]);

    const addReview = async (newReview) => {
        console.log(newReview);
        try {
            const response = await fetch(`http://localhost:8080/api/v1/review/${film_type}/${id}/addReview`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify(newReview),
            });

            if (!response.ok) {
                throw new Error('Failed to add review');
            }

            // Refresh the reviews after successfully adding a review
            queryClient.invalidateQueries(['reviews', type, id]);
        } catch (error) {
            console.error('Error adding review:', error);
        }
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

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error fetching reviews</div>;

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
                <div key={review.reviewId} className="border-b border-gray-700 py-6 pb-10">
                    <div className="flex items-center pb-5">
                        <img src={`https://i.pravatar.cc/50?img=${index + 1}`} alt={review.username} className="w-10 h-10 rounded-full mr-3" />
                        <div>
                            <p className="font-bold">{review.username}</p>
                            <div className="flex">
                                {renderStars(review.rating)}
                            </div>
                        </div>
                    </div>
                    <div className="text-gray-200 whitespace-pre-wrap">
                        <p>{review.reviewText || ''}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};