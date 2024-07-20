// Login.jsx

import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { useSetSessionCookie } from '../../components/useSessionCookies';

async function loginUser(credentials) {
  const response = await fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(credentials),
    credentials: 'include', // Include cookies in the request
  });

  if (!response.ok) {
    // Throw an error with response status
    const error = new Error('Login failed');
    error.status = response.status;
    throw error;
  }

  return response.json();
}

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const setSessionCookie = useSetSessionCookie(); // Use the custom hook

  const { mutate, isPending } = useMutation({
    mutationFn: (credentials) => loginUser(credentials),
    onSuccess: (data) => {
      // Set the session cookie and redirect to the desired page after successful login.
      setSessionCookie(data);
      window.location.href = '/';
    },
    onError: (error) => {
      if (error.status === 401) {
        alert("Invalid username or password.");
      } else {
        alert('Server error. Please try again later.');
      }
    },
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    mutate({ username, password, rememberMe });
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-[#14181d]">
      <div className="w-full max-w-md p-8 space-y-8 bg-gray-800 rounded-lg shadow-md mt-[-300px]">
        <h2 className="text-2xl font-extrabold text-center text-gray-200">Sign in</h2>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <input type="hidden" name="remember" value="true" />
          <div className="rounded-md shadow-sm space-y-6">
            <div>
              <label htmlFor="username" className="block text-sm font-medium text-gray-200">Username</label>
              <input
                id="username"
                name="username"
                type="text"
                autoComplete="username"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="relative block w-full px-3 py-2 mt-1 text-gray-900 placeholder-gray-500 bg-gray-100 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="Enter your username"
              />
            </div>
            <div>
              <label htmlFor="password" className="block text-sm font-medium text-gray-200">Password</label>
              <input
                id="password"
                name="password"
                type="password"
                autoComplete="current-password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="relative block w-full px-3 py-2 mt-1 text-gray-900 placeholder-gray-500 bg-gray-100 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="Enter your password"
              />
            </div>
          </div>
          <div className="flex items-center justify-between">
            <div className="flex items-center">
              <input
                id="remember-me"
                name="remember-me"
                type="checkbox"
                checked={rememberMe}
                onChange={(e) => setRememberMe(e.target.checked)}
                className="w-4 h-4 text-indigo-600 border-gray-300 rounded focus:ring-indigo-500"
              />
              <label htmlFor="remember-me" className="block ml-2 text-sm text-gray-200">
                Remember me
              </label>
            </div>
            <div className="text-sm">
              <a href="#" className="font-medium text-indigo-400 hover:text-indigo-500">
                Forgot your password?
              </a>
            </div>
          </div>
          <div>
            <button
              type="submit"
              disabled={isPending}
              className={`relative flex justify-center w-full px-4 py-2 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md group focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${isPending ? 'bg-gray-400 cursor-not-allowed' : 'hover:bg-indigo-700'}`}
            >
              {isPending ? 'Signing In...' : 'Sign In'}
            </button>
          </div>
        </form>
        <div className="mt-6 text-center">
          <p className="text-sm text-gray-200">
            Don't have an account?{' '}
            <a href="/register" className="font-medium text-indigo-400 hover:text-indigo-500">
              Sign Up
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}
