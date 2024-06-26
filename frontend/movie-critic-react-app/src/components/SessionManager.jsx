import React, { useEffect } from 'react';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import Cookies from 'js-cookie';
import { setSessionCookie, clearSessionCookie, fetchSession } from './SessionUtils';

function useSessionQuery(sessionChecked) {
  return useQuery({
    queryFn: () => fetchSession(),
    queryKey: ['session'],
    enabled: !sessionChecked,
    retry: false,
    onSuccess: (data) => {
      setSessionCookie(data);
    },
    onError: () => {
      clearSessionCookie();
      console.log("Accessed")
      Cookies.set('sessionChecked', true);
    },
  });
}

export default function SessionManager({ children }) {
  const queryClient = useQueryClient();
  const sessionChecked = Cookies.get('sessionChecked');
  useSessionQuery(sessionChecked);

  useEffect(() => {
    if (!sessionChecked) {
      queryClient.invalidateQueries('session');
    }
  }, [sessionChecked, queryClient]);

  return <>{children}</>;
}
