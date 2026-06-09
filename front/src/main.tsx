import './styles/index.css';

import { ThemeProvider } from '@mui/material';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';

import { store } from '@/store/store.ts';

import App from './App.js';
import { theme } from './shared/components/Theme.ts';

const rootElement = document.getElementById('root');

if (!rootElement) {
    throw new Error('Root element not found. Check your index.html');
}

createRoot(rootElement).render(
    <StrictMode>
        <BrowserRouter>
            <ThemeProvider theme={theme}>
                <Provider store={store}>
                    <App />
                </Provider>
            </ThemeProvider>
        </BrowserRouter>
    </StrictMode>
);
