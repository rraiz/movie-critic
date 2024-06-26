import Cookies from 'js-cookie';

export function setSessionCookie(data) {
  Cookies.set('username', data.username);
  Cookies.set('authenticated', data.authenticated);
  Cookies.set('sessionChecked', true);
}

export function clearSessionCookie() {
  Cookies.remove('username');
  Cookies.remove('authenticated');
}

export function isLoggedIn() {
  return Cookies.get('authenticated') === 'true' && Cookies.get('sessionChecked') === 'true';
}

export function getUsername() {
  return Cookies.get('username');
}

export async function fetchSession() {
  const response = await fetch('http://localhost:8080/user/check-session', {
    credentials: 'include'
  });
  if (!response.ok) {
    throw new Error('Invalid session');
  }
  return response.json();
}
