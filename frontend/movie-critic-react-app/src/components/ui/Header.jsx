import { useState } from 'react';

import { FaSearch } from 'react-icons/fa';

function Header() {
  const [searchQuery, setSearchQuery] = useState('');

  function onSubmit(e) {
    e.preventDefault();
    const query = encodeURIComponent(searchQuery);
    window.location.href = `/search?query=${query}`;
  }

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
            <a className=" hover:text-gray-400" href="/films">
              Films
            </a>
          </li>
          <li>
            <a className=" hover:text-gray-400" href="/about">
              About
            </a>
          </li>
          <li>
            <a className=" hover:text-gray-400" href="/film">
              Sign In
            </a>
          </li>
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

export default Header;
