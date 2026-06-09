import { Alert, Snackbar } from '@mui/material';

interface SnackbarProps {
    isOpen: boolean;
    title: string;
    severity: 'success' | 'info' | 'warning' | 'error';
    onClose: () => void;
}

const Snack = ({ isOpen, title, severity = 'success', onClose = () => {} }: SnackbarProps) => {
    return (
        <Snackbar open={isOpen} onClose={onClose} autoHideDuration={5000}>
            <Alert severity={severity} variant="filled">
                {title}
            </Alert>
        </Snackbar>
    );
};

export default Snack;
