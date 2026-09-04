import { Box, CircularProgress } from '@mui/material';

const Loader = () => {
    return (
        <Box
            sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                width: '100vw',
                height: '100vh',
            }}
        >
            <CircularProgress size="6rem" aria-label="Loading…" />
        </Box>
    );
};

export default Loader;
