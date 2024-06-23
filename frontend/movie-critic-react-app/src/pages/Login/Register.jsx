import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';

export default function SignUpPage() {
  const [errors, setErrors] = useState({});

  const mutation = useMutation({
    mutationFn: (userInfo) => registerUser(userInfo),
    onError: (error) => {
      console.error('Error:', error);
      alert('Server error. Please try again later.');
    },
    onSuccess: (data) => {
      if (Object.keys(data).length === 0) {
        setErrors({ username: 'Username already exists' });
        console.log('Error:', data);
      } else {
        console.log('Success:', data);
        window.location.href = '/login';
      }
    },
  });

  const submitForm = (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);
    const payload = Object.fromEntries(formData.entries());

    const username = payload.username;
    const email = payload.email;
    const password = payload.password;
    const confirmPassword = payload['confirm-password'];

    let newErrors = {};

    // Username validations
    if (username.length < 5 || username.length > 20) {
      newErrors.username = 'Username must be between 5 to 20 characters.';
    } else if (!/^[a-zA-Z0-9._]+$/.test(username)) {
      newErrors.username = 'Username can only contain letters, numbers, underscores, and periods.';
    } else if (/^[_.]/.test(username) || /[_.]$/.test(username)) {
      newErrors.username = 'Username cannot start or end with an underscore or period.';
    } else if (/([_.]{2,})/.test(username)) {
      newErrors.username = 'Username cannot have consecutive underscores or periods.';
    }

    // Email validation
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(email)) {
      newErrors.email = 'Please enter a valid email address.';
    }

    // Password validations
    if (password.length < 8 || password.length > 20) {
      newErrors.password = 'Password must be between 8 to 20 characters.';
    } else if (!/(?=.*[a-z])/.test(password)) {
      newErrors.password = 'Password must contain at least one lowercase letter.';
    } else if (!/(?=.*[A-Z])/.test(password)) {
      newErrors.password = 'Password must contain at least one uppercase letter.';
    } else if (!/(?=.*\d)/.test(password)) {
      newErrors.password = 'Password must contain at least one digit.';
    } else if (!/(?=.*[@$!%*?&])/.test(password)) {
      newErrors.password = 'Password must contain at least one special character.';
    }

    // Confirm password validation
    if (password !== confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match.';
    }

    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      mutation.mutate({ username, email, password });
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-[#14181d]">
      <div className="w-full max-w-md p-8 space-y-8 bg-gray-800 rounded-lg shadow-md mt-[-172px]">
        <h2 className="text-2xl font-extrabold text-center text-gray-200">Create your account</h2>
        <form className="mt-8 space-y-6" onSubmit={submitForm} noValidate>
          <div className="rounded-md shadow-sm space-y-6">
            <div>
              <label htmlFor="username" className="block text-sm font-medium text-gray-200">Username</label>
              <input
                id="username"
                name="username"
                type="text"
                autoComplete="username"
                required
                className="relative block w-full px-3 py-2 mt-1 text-gray-900 placeholder-gray-500 bg-gray-100 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="Enter your username"
              />
              <p className="text-red-500 text-sm min-h-[20px]">{errors.username}</p>
            </div>
            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-200">Email</label>
              <input
                id="email"
                name="email"
                type="email"
                autoComplete="email"
                required
                className="relative block w-full px-3 py-2 mt-1 text-gray-900 placeholder-gray-500 bg-gray-100 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="Enter your email"
              />
              <p className="text-red-500 text-sm min-h-[20px]">{errors.email}</p>
            </div>
            <div>
              <label htmlFor="password" className="block text-sm font-medium text-gray-200">Password</label>
              <input
                id="password"
                name="password"
                type="password"
                autoComplete="new-password"
                required
                className="relative block w-full px-3 py-2 mt-1 text-gray-900 placeholder-gray-500 bg-gray-100 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="Enter your password"
              />
              <p className="text-red-500 text-sm min-h-[20px]">{errors.password}</p>
            </div>
            <div>
              <label htmlFor="confirm-password" className="block text-sm font-medium text-gray-200">Confirm Password</label>
              <input
                id="confirm-password"
                name="confirm-password"
                type="password"
                autoComplete="new-password"
                required
                className="relative block w-full px-3 py-2 mt-1 text-gray-900 placeholder-gray-500 bg-gray-100 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="Confirm your password"
              />
              <p className="text-red-500 text-sm min-h-[20px]">{errors.confirmPassword}</p>
            </div>
          </div>
          <div>
            <button
              type="submit"
              className="relative flex justify-center w-full px-4 py-2 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md group hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              Sign Up
            </button>
          </div>
        </form>
        <div className="mt-6 text-center">
          <p className="text-sm text-gray-200">
            Already have an account?{' '}
            <a href="/login" className="font-medium text-indigo-400 hover:text-indigo-500">
              Sign in
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}

async function registerUser(userInfo) {
  const response = await fetch('http://localhost:8080/auth/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userInfo),
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const text = await response.text();
  return text ? JSON.parse(text) : {};
}
