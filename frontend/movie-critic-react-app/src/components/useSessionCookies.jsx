// useSessionCookies.jsx

import { useCookies } from 'react-cookie';

export function useSetSessionCookie() {
  const [cookies, setCookie] = useCookies(['username', 'authenticated', 'sessionChecked']);

  const setSessionCookie = (data) => {
    setCookie('username', data.username, { path: '/' });
    setCookie('authenticated', data.authenticated, { path: '/' });
    setCookie('sessionChecked', true, { path: '/' });
  };

  return setSessionCookie;
}

export function useClearSessionCookie() {
  const [cookies, setCookie, removeCookie] = useCookies(['username', 'authenticated', 'sessionChecked']);

  const clearSessionCookie = () => {
    removeCookie('username', { path: '/' });
    removeCookie('authenticated', { path: '/' });
    setCookie('sessionChecked', true, { path: '/' });
  };

  return clearSessionCookie;
}

export function useIsLoggedIn() {
  const [cookies] = useCookies(['authenticated', 'sessionChecked']);

  const isLoggedIn = () => {
    return cookies.authenticated === 'true' && cookies.sessionChecked === 'true';
  };

  return isLoggedIn;
}

export function useGetUsername() {
  const [cookies] = useCookies(['username']);

  const getUsername = () => {
    return cookies.username;
  };

  return getUsername;
}
