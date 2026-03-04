import type { CredentialResponse } from '@react-oauth/google';

import axios from 'axios';
import { toast } from 'react-toastify';
import { useState, useCallback } from 'react';
import { GoogleLogin } from '@react-oauth/google';

import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import InputAdornment from '@mui/material/InputAdornment';

import { useRouter } from 'src/routes/hooks';

import { Iconify } from 'src/components/iconify';

// ----------------------------------------------------------------------

export function SignInView() {
  const router = useRouter();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  // ---------- Email/password login ----------
  const handleSignIn = useCallback(async () => {
    try {
      const res = await axios.post('http://localhost:8081/auth/login', { email, password });
      const token = res.data.token;

      localStorage.setItem('token', token); // save JWT
      toast.success('Login successful!');
      router.push('/dashboard'); // navigate to dashboard
    } catch (err: any) {
      console.error(err);
      toast.error(err.response?.data?.message || err.message || 'Something went wrong');
    }
  }, [email, password, router]);

  // ---------- Google login ----------
  const handleGoogleLogin = async (credentialResponse: CredentialResponse) => {
    try {
      if (!credentialResponse.credential) {
        toast.error('Google login failed');
        return;
      }

      const res = await axios.post('http://localhost:8081/auth/login/google', {
        idToken: credentialResponse.credential,
      });

      const token = res.data.token;
      localStorage.setItem('token', token); // store JWT
      toast.success('Logged in with Google!');
      router.push('/dashboard');
    } catch (err: any) {
      toast.error(err.response?.data?.message || err.message || 'Google login failed');
    }
  };

  // ---------- Render form ----------
  const renderForm = (
    <Box
      sx={{
        display: 'flex',
        alignItems: 'flex-end',
        flexDirection: 'column',
      }}
    >
      <TextField
        fullWidth
        name="email"
        label="Email address"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        sx={{ mb: 3 }}
        slotProps={{ inputLabel: { shrink: true } }}
      />

      <Link variant="body2" color="inherit" sx={{ mb: 1.5 }}>
        Forgot password?
      </Link>

      <TextField
        fullWidth
        name="password"
        label="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        type={showPassword ? 'text' : 'password'}
        slotProps={{
          inputLabel: { shrink: true },
          input: {
            endAdornment: (
              <InputAdornment position="end">
                <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                  <Iconify icon={showPassword ? 'solar:eye-bold' : 'solar:eye-closed-bold'} />
                </IconButton>
              </InputAdornment>
            ),
          },
        }}
        sx={{ mb: 3 }}
      />

      <Button
        fullWidth
        size="large"
        variant="contained"
        onClick={handleSignIn}
      >
        Sign in
      </Button>
    </Box>
  );

  return (
    <>
      <Box
        sx={{
          gap: 1.5,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          mb: 5,
        }}
      >
        <Typography variant="h5">Sign in</Typography>
        <Typography
          variant="body2"
          sx={{ color: 'text.secondary' }}
        >
          Don’t have an account?
          <Link variant="subtitle2" sx={{ ml: 0.5 }}>
            Get started
          </Link>
        </Typography>
      </Box>

      {renderForm}

      <Divider sx={{ my: 3, '&::before, &::after': { borderTopStyle: 'dashed' } }}>
        <Typography
          variant="overline"
          sx={{ color: 'text.secondary', fontWeight: 'fontWeightMedium' }}
        >
          OR
        </Typography>
      </Divider>

      <Box
        sx={{
          gap: 1,
          display: 'flex',
          justifyContent: 'center',
        }}
      >
        <GoogleLogin
          onSuccess={handleGoogleLogin}
          onError={() => toast.error('Google login failed')}
          useOneTap
          shape="rectangular"
        />
      </Box>
    </>
  );
}