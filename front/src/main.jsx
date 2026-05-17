import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './styles/index.css'
import App from './App.jsx'
import {ThemeProvider} from '@mui/material';
import {theme} from "./shared/components/Theme.js";
import {BrowserRouter} from "react-router-dom";
import AppProvider from "./shared/context/AppProvider.jsx";

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <ThemeProvider theme={theme}>
                <AppProvider>
                    <App />
                </AppProvider>
            </ThemeProvider>
        </BrowserRouter>
    </StrictMode>,
)
