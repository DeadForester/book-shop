import {Box, Grid} from "@mui/material";

const BookImage = ({poster, name}) => {
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
                    bgcolor: 'grey.100',
                }}
            />
        </Grid>
    );
};

export default BookImage;