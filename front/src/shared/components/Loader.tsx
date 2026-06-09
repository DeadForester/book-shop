import { Box, CircularProgress } from '@mui/material';

const Loader = () => {
    return (
        <Box
            sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                width: '100%',
                height: '100%',
            }}
        >
            <CircularProgress size="3rem" aria-label="Loading…" />
        </Box>
    );
};

export default Loader;
