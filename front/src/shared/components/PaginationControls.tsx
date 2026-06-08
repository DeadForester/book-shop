import { Box, Pagination } from '@mui/material';
import { ChangeEvent } from 'react';

interface PaginationControlsProps {
    page: number;
    totalPages: number;
    onPageChange: (event: ChangeEvent<unknown, Element>, page: number) => void;
}

const PaginationControls = ({ page, totalPages, onPageChange }: PaginationControlsProps) => {
    if (totalPages <= 1) return <></>;

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
