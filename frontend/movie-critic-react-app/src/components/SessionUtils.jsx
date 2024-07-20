// SessionUtils.jsx

export async function fetchSession() {
  const response = await fetch('http://localhost:8080/user/check-session', {
    credentials: 'include'
  });
  if (!response.ok) {
    throw new Error('Invalid session');
  }
  return response.json();
}

export async function logout() {
  const response = await fetch('http://localhost:8080/user/logout', {
    credentials: 'include'
  });
  if (!response.ok) {
    throw new Error('Logout failed');
  }
  return response;
}
