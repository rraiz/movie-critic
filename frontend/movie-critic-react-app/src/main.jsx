import React from 'react'
import ReactDOM from 'react-dom/client'
import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import './assets/index.css'

import Header from './components/ui/Header.jsx'
import Footer from './components/ui/Footer.jsx'

import Home from './pages/Home/Home.jsx'
import Error from './pages/Error/Error.jsx'



const router = createBrowserRouter([{
  path: '/',
  element: <Home />,
  errorElement: <Error />
},
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
  <Header />
  <RouterProvider router={router}></RouterProvider>
  <Footer />
  </React.StrictMode>,
)
