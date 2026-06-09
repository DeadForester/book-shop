import { Box, Grid } from '@mui/material';

interface BookImageProps {
    poster: string;
    name: string;
}

const BookImage = ({ poster, name }: BookImageProps) => {
    return (
        <Grid size={{ xs: 12, md: 5 }}>
            <Box
                component="img"
                src={poster}
                alt={name}
                sx={{
                    width: '100%',
                    maxHeight: { xs: '300px', md: '500px' },
                    objectFit: 'contain',
                    borderRadius: 2,
                    boxShadow: 2,
                    backgroundColor: 'grey.100',
                }}
            />
        </Grid>
    );
};

export default BookImage;
