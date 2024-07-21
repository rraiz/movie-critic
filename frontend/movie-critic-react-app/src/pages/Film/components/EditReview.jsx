import { useState, useEffect, useRef } from 'react';
import { FaStar, FaStarHalf, FaTrash, FaEdit } from 'react-icons/fa';

const EditReview = ({ review, onSave, onDelete, username, isEditing, startEditing, stopEditing }) => {
    const [editingReview, setEditingReview] = useState(review.reviewText);
    const [editingRating, setEditingRating] = useState(review.rating);
    const textareaRef = useRef(null);

    useEffect(() => {
        if (isEditing && textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = textareaRef.current.scrollHeight + 'px';
        }
    }, [isEditing, editingReview]);

    const handleTextareaChange = (e) => {
        setEditingReview(e.target.value);
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = textareaRef.current.scrollHeight + 'px';
        }
    };

    const handleStarClick = (e, starIndex) => {
        const star = e.target;
        const rect = star.getBoundingClientRect();
        const clickX = e.clientX - rect.left;

        if (clickX < rect.width / 2) {
            setEditingRating(starIndex - 0.5);
        } else {
            setEditingRating(starIndex);
        }
    };

    const renderStars = (rating, editable) => {
        const stars = [];
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars.push(
                    <FaStar
                        key={i}
                        className="text-yellow-500 w-4 h-4 cursor-pointer"
                        onClick={(e) => editable && handleStarClick(e, i)}
                    />
                );
            } else if (i === Math.ceil(rating) && !Number.isInteger(rating)) {
                stars.push(
                    <div key={i} className="relative w-4 h-4 cursor-pointer" onClick={(e) => editable && handleStarClick(e, i)}>
                        <FaStar className="text-gray-500 w-4 h-4 absolute top-0 left-0" />
                        <FaStarHalf className="text-yellow-500 w-4 h-4 absolute top-0 left-0" />
                    </div>
                );
            } else {
                stars.push(
                    <FaStar
                        key={i}
                        className="text-gray-500 w-4 h-4 cursor-pointer"
                        onClick={(e) => editable && handleStarClick(e, i)}
                    />
                );
            }
        }
        return stars;
    };

    return (
        <div className="border-b border-gray-700 py-6 pb-10">
            <div className="flex items-center pb-5">
                <img src={`https://i.pravatar.cc/50?img=${review.index + 1}`} alt={review.username} className="w-10 h-10 rounded-full mr-3" />
                <div>
                    <p className="font-bold">{review.username}</p>
                    <div className="flex">
                        {renderStars(isEditing ? editingRating : review.rating, isEditing)}
                    </div>
                </div>
            </div>
            <div className="text-gray-200 whitespace-pre-wrap">
                {isEditing ? (
                    <textarea
                        ref={textareaRef}
                        value={editingReview}
                        onChange={handleTextareaChange}
                        className="mt-1 w-full rounded-md bg-gray-800 focus:outline-none focus:bg-[#2e3d51bd] pl-2 pt-2 pr-2 pb-2 resize-none overflow-hidden"
                        rows="4"
                    ></textarea>
                ) : (
                    <p>{review.reviewText || ''}</p>
                )}
            </div>
            {username === review.username && (
                <div className="flex justify-end mt-2">
                    {isEditing ? (
                        <button
                            onClick={() => { onSave(review.reviewId, editingReview, editingRating); stopEditing(); }}
                            className="text-green-500 hover:text-green-700 mr-2"
                        >
                            Save
                        </button>
                    ) : (
                        <button
                            onClick={() => startEditing(review.reviewId)}
                            className="text-gray-600 hover:text-gray-700 mr-2"
                        >
                            <FaEdit />
                        </button>
                    )}
                    <button
                        onClick={() => onDelete(review.reviewId)}
                        className="text-gray-600 hover:text-gray-700"
                    >
                        <FaTrash />
                    </button>
                </div>
            )}
        </div>
    );
};

export default EditReview;
