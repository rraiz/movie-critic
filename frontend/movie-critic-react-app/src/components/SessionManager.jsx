// SessionManager.jsx

import { useMutation } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useSetSessionCookie, useClearSessionCookie } from './useSessionCookies';
import { fetchSession } from './SessionUtils';

function useSessionMutation() {
  const setSessionCookie = useSetSessionCookie();
  const clearSessionCookie = useClearSessionCookie();

  return useMutation({
    mutationFn: fetchSession,
    mutationKey: ['session'],
    onSuccess: (data) => {
      setSessionCookie(data);
    },
    onError: () => {
      clearSessionCookie();
    },
  });
}

export default function SessionManager({ children }) {
  const setSessionCookie = useSetSessionCookie();
  const { mutate } = useSessionMutation();

  useEffect(() => {
    if (!document.cookie.includes('sessionChecked=true')) {
      console.log('Checking session...');
      mutate();
      setSessionCookie({ sessionChecked: true });
    }
  }, [mutate, setSessionCookie]);

  return <>{children}</>;
}
