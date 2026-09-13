import { Alert, Snackbar } from '@mui/material';

interface SnackbarProps {
    /**  Показывается ли уведомление */
    isOpen: boolean;
    /** Тест уведомления */
    title: string;
    /** Цвет уведомления */
    severity: 'success' | 'info' | 'warning' | 'error';
    /** Действие при закрытии уведомления */
    onClose: () => void;
    /** Время до автоматического закрытия */
    duration?: number;
}

const Snack = ({ isOpen, title, severity = 'success', onClose = () => {}, duration = 5000 }: SnackbarProps) => {
    return (
        <Snackbar open={isOpen} onClose={onClose} autoHideDuration={duration}>
            <Alert severity={severity} variant="filled">
                {title}
            </Alert>
        </Snackbar>
    );
};

export default Snack;
