import { Box, Pagination } from "@mui/material";

const PaginationControls = ({ page, totalPages, onPageChange }) => {
    if (totalPages <= 1) return null;

    return (
        <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
            <Pagination
                count={totalPages}
                page={page}
                onChange={onPageChange}
                color="primary"
                size="large"
                showFirstButton
                showLastButton
                siblingCount={1}
                boundaryCount={1}
            />
        </Box>
    );
};

export default PaginationControls;