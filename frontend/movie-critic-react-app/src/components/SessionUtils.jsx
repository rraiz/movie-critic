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
