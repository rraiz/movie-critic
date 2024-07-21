import { useEffect, useState } from 'react';
import { useQuery, useQueryClient, QueryClient, QueryClientProvider } from '@tanstack/react-query';
import WriteReview from './components/WriteReview';
import EditReview from './components/EditReview'; 
import { useIsLoggedIn, useGetUsername } from '../../components/useSessionCookies';
import { FaStar, FaStarHalf } from 'react-icons/fa';

// Create a client
const queryClient = new QueryClient();

export default function UserReviews({ type, id }) {
    const isLoggedIn = useIsLoggedIn();
    const getUsername = useGetUsername();
    const username = getUsername();
    const queryClient = useQueryClient();
    const [averageRating, setAverageRating] = useState(0);
    const [editingReviewId, setEditingReviewId] = useState(null); // State to track the review being edited

    const film_type = type === 'movie' ? 0 : 1;

    const { data: reviews = [], isLoading, error } = useQuery({
        queryKey: ['reviews', film_type, id],
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
            queryClient.invalidateQueries(['reviews', film_type, id]);
        } catch (error) {
            console.error('Error adding review:', error);
        }
    };

    const editReview = async (reviewId, updatedReviewText, updatedRating) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/review/${film_type}/${id}/editReview/${reviewId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ reviewText: updatedReviewText, rating: updatedRating }),
            });

            if (!response.ok) {
                throw new Error('Failed to edit review');
            }

            // Refresh the reviews after successfully editing a review
            queryClient.invalidateQueries(['reviews', film_type, id]);
        } catch (error) {
            console.error('Error editing review:', error);
        }
    };

    const deleteReview = async (reviewId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/review/${film_type}/${id}/deleteReview/${reviewId}`, {
                method: 'DELETE',
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error('Failed to delete review');
            }

            // Refresh the reviews after successfully deleting a review
            queryClient.invalidateQueries(['reviews', film_type, id]);
        } catch (error) {
            console.error('Error deleting review:', error);
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
                <EditReview
                    key={review.reviewId}
                    review={{ ...review, index }}
                    onSave={editReview}
                    onDelete={deleteReview}
                    username={username}
                    isEditing={editingReviewId === review.reviewId}
                    startEditing={setEditingReviewId}
                    stopEditing={() => setEditingReviewId(null)}
                />
            ))}
        </div>
    );
}
