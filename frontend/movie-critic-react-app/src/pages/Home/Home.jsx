import { useIsLoggedIn, useGetUsername } from '../../components/useSessionCookies';
import { useQuery } from '@tanstack/react-query';
import FilmList from './FilmList';

export default function Home() {
  const isLoggedIn = useIsLoggedIn(); // Use the custom hook
  const getUsername = useGetUsername(); // Use the custom hook

  const { data: popularMovies, error: moviesError, isLoading: moviesLoading } = usePopularMovies();
  const { data: popularTVShows, error: tvError, isLoading: tvLoading } = usePopularTVShows();

  return (
    <div className="bg-[#14181d]">
      <main className='text-[#FFFFFF] min-h-screen max-w-[1000px] mx-auto'>
        <div className='mb-[50px]'>
          <h1 className="font-bold text-[50px] text-[#FFFFFF] text-center">
            {isLoggedIn() ? `Welcome Back, ${getUsername()}!` : 'Welcome to Movie Critic'}
          </h1>
          <p className='text-center'> 
            {isLoggedIn() ? 
            'Check out the latest reviews, share your opinions, and find new movies and TV shows tailored just for you.'
            : 'Here you can find reviews of the latest films, share your own opinionsm, and discover new movies and TV shows to watch.'}
          </p>        
        </div>




        {moviesLoading ? (
          <div className="text-white text-center">Loading Popular Movies...</div>
        ) : (
          <FilmList title="Popular Movies" films={popularMovies} />
        )}

        {tvLoading ? (
          <div className="text-white text-center">Loading Popular TV Shows...</div>
        ) : (
          <FilmList title="Popular TV Shows" films={popularTVShows} />
        )}

        {moviesError && <div className="text-white text-center">Error loading popular movies: {moviesError.message}</div>}
        {tvError && <div className="text-white text-center">Error loading popular TV shows: {tvError.message}</div>}

      </main>
    </div>
  );
}


const usePopularMovies = () => {
  return useQuery({
    queryKey: ['popularMovies'],
    queryFn: () => fetchPopularFilms('movies')
  });
};

const usePopularTVShows = () => {
  return useQuery({
    queryKey: ['popularTVShows'],
    queryFn: () => fetchPopularFilms('tv')
  });
};


const fetchPopularFilms = async (type) => {
  const response = await fetch(`http://localhost:8080/api/v1/popular/${type}`);
  if (!response.ok) {
      throw new Error('Network response was not ok');
  }
  return response.json();
};