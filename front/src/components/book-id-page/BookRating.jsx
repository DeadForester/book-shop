import {Box, Rating, Typography} from "@mui/material";

const BookRating = ({rating, reviewsCount}) => {
    return (
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 2 }}>
            <Rating value={rating || 4.5} precision={0.5} readOnly size="small" />
            <Typography variant="body2" color="text.secondary">
                ({reviewsCount || '0'} отзывов)
            </Typography>
        </Box>
    );
};

export default BookRating;