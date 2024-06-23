import React from 'react'
import ReactDOM from 'react-dom/client'
import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import {QueryClient, QueryClientProvider} from '@tanstack/react-query'
import './assets/index.css'

import Header from './components/ui/Header.jsx'
import Footer from './components/ui/Footer.jsx'

import Home from './pages/Home/Home.jsx'
import Film from './pages/Film/Film.jsx'
import Error from './pages/Error/Error.jsx'
import Search from './pages/Search/Search.jsx'
import Login from './pages/Login/Login.jsx'
import Register from './pages/Login/Register.jsx'


const queryClient = new QueryClient();

const router = createBrowserRouter([{
  path: '/',
  element: <Home />,
  errorElement: <Error />
},
{
  path: '/tv/:id',
  element: <Film type={"tv"}/>,
},
{
  path: '/movie/:id',
  element: <Film type={"movie"}/>,
},
{
  path: '/search',
  element: <Search />,
},
{
  path: '/login',
  element: <Login />,
},
{
  path: '/register',
  element: <Register />,
}
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <Header />
      <RouterProvider router={router}></RouterProvider>
      <Footer />
    </QueryClientProvider>
  </React.StrictMode>,
)
