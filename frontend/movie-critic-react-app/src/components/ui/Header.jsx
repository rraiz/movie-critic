import { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import { useIsLoggedIn, useClearSessionCookie, useGetUsername} from '../useSessionCookies';
import { logout } from '../SessionUtils';

export default function Header() {
  const [searchQuery, setSearchQuery] = useState('');
  const isLoggedIn = useIsLoggedIn(); // Use the custom hook
  const clearSessionCookie = useClearSessionCookie();
  const getUsername = useGetUsername();

  function onSubmit(e) {
    e.preventDefault();
    const query = encodeURIComponent(searchQuery);
    window.location.href = `/search?query=${query}`;
  }

  const handleLogout = async () => {
    clearSessionCookie();
    const log = logout();
    window.location.href = '/';
  };

  return (
    <header className="bg-[#14181d] p-4">
      <nav className="max-w-[825px] mx-auto ">
        <ul className="flex items-center justify-between text-[#dfe6eb] ">
          <li>
            <h1 className=" text-3xl font-bold">Movie Critic</h1>
          </li>
          <li>
            <a className=" hover:text-gray-400" href="/">
              Home
            </a>
          </li>
          <li>
            <a className=" hover:text-gray-400" href="/about">
              About
            </a>
          </li>
          
          {isLoggedIn() ? (
            <li>
              <a className="hover:text-gray-400" href={`/${getUsername()}`}>
                My List
              </a>
            </li>
          ) : (
            null
          )}


          {isLoggedIn() ? (
          <li>
            <button 
              className="hover:text-gray-400"
              onClick={handleLogout}
            >
              Logout
            </button>
          </li>
        ) : (
          <li>
            <a className="hover:text-gray-400" href="/login">
              Sign In
            </a>
          </li>
        )}

          <li>
            <form onSubmit={onSubmit} className="relative flex items-center">
              <input
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className=" text-[15px] w-[200px] pl-4 pr-8 py-[4px] bg-[#2c323b] text-white border-none rounded-[10px] focus:outline-none focus:bg-[#e5e5e7] focus:text-black"
                placeholder="Search"
              />
              <button
                type="submit"
                className="absolute right-3 bg-transparent border-none cursor-pointer"
              >
                <FaSearch className="text-gray-500" />
              </button>
            </form>
          </li>
        </ul>
      </nav>
      <div className="border-t border-[#384148] my-4"></div>
    </header>
  );
}

