import { AlertColor } from '@mui/material';

export interface SnackBar {
    open: boolean;
    message: string;
    severity: AlertColor;
}
