import type { ReactNode } from 'react';

import { Navigate } from 'react-router-dom';

type Props = {
  children: ReactNode; // safer than JSX.Element
};

export default function AuthGuard({ children }: Props) {
  const token = localStorage.getItem('token');

  if (!token) {
    return <Navigate to="/sign-in" replace />;
  }

  return <>{children}</>;
}