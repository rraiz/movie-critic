import { useIsLoggedIn, useGetUsername } from '../../components/useSessionCookies'; // Adjust the import path as needed

export default function Home() {
  const isLoggedIn = useIsLoggedIn(); // Use the custom hook
  const getUsername = useGetUsername(); // Use the custom hook

  return (
    <div className="bg-[#14181d] text-[#FFFFFF] text-center min-h-screen">
      <main>
        <h1 className="font-bold text-[50px] text-[#FFFFFF]">
          {isLoggedIn() ? `Welcome Back, ${getUsername()}!` : 'Welcome to Movie Critic'}
        </h1>
        <p>Here you can find reviews of the latest movies, and share your own opinions!</p>
      </main>
    </div>
  );
}
