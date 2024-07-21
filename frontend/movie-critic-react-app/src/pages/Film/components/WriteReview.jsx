import { useState } from 'react';
import { useGetUsername } from '../../../components/useSessionCookies';
import { FaStar, FaStarHalf } from 'react-icons/fa';

const WriteReview = ({ addReview }) => {

    const username = useGetUsername();

    const [newReview, setNewReview] = useState({rating: 0, reviewText: '' });
    const [isFormVisible, setFormVisible] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleReviewChange = (e) => {
        const { name, value } = e.target;
        autoResize(e);
        setNewReview({ ...newReview, [name]: value });
    };

    const handleRatingChange = (rating) => {
        setNewReview({ ...newReview, rating });
    };

    const handleStarClick = (e, starIndex) => {
        const star = e.target;
        const rect = star.getBoundingClientRect();
        const clickX = e.clientX - rect.left;

        // Check if the click is on the left or right half of the star
        if (clickX < rect.width / 2) {
            handleRatingChange(starIndex - 0.5);
        } else {
            handleRatingChange(starIndex);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (newReview.rating < 0.5) {
            setErrorMessage('Please provide a rating of at least 0.5 stars.');
            return;
        }
        addReview(newReview);
        setNewReview({rating: 0, reviewText: '' });
        setFormVisible(false);
        setErrorMessage('');
    };

    const autoResize = (e) => {
        e.target.style.height = 'auto';
        e.target.style.height = `${e.target.scrollHeight}px`;
    };

    const renderStars = (rating) => {
        const stars = [];
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars.push(
                    <FaStar
                        key={i}
                        className="text-yellow-500 w-5 h-5 cursor-pointer"
                        onClick={(e) => handleStarClick(e, i)}
                    />
                );
            } else if (i === Math.ceil(rating) && !Number.isInteger(rating)) {
                stars.push(
                    <div key={i} className="relative w-5 h-5 cursor-pointer" onClick={(e) => handleStarClick(e, i)}>
                        <FaStar className="text-gray-500 w-5 h-5 absolute top-0 left-0" />
                        <FaStarHalf className="text-yellow-500 w-5 h-5 absolute top-0 left-0" />
                    </div>
                );
            } else {
                stars.push(
                    <FaStar
                        key={i}
                        className="text-gray-500 w-5 h-5 cursor-pointer"
                        onClick={(e) => handleStarClick(e, i)}
                    />
                );
            }
        }
        return stars;
    };

    return (
        <div>
            <button
                className="bg-gray-700 hover:bg-gray-600 text-white font-semibold py-2 px-4 rounded-full mt-2 mb-2"
                onClick={() => setFormVisible(!isFormVisible)}
            >
                Write a Review
            </button>
            {isFormVisible && (
                <form onSubmit={handleSubmit} className="mt-4">
                    <div className="mb-4">
                        <label className="block text-md font-medium text-gray-200 pb-1">Rating</label>
                        <div className="flex">
                            {renderStars(newReview.rating)}
                        </div>
                        {errorMessage && <p className="text-red-500 text-sm mt-2">{errorMessage}</p>}
                    </div>
                    <div className="mb-4">
                        <label className="block text-md font-medium text-gray-200 pt-2">Review (optional)</label>
                        <textarea
                            name="reviewText"
                            value={newReview.reviewText}
                            onChange={handleReviewChange}
                            className="mt-1 w-full rounded-md bg-gray-800 focus:outline-none focus:bg-[#2e3d51bd] pl-2 pt-2 pr-2 pb-2 resize-none"
                            rows="4"
                        ></textarea>
                    </div>
                    <button
                        type="submit"
                        className="px-4 py-2 bg-green-700 text-white rounded-full hover:bg-green-600 font-semibold"
                    >
                        Submit Review
                    </button>
                </form>
            )}
        </div>
    );
};

export default WriteReview;
