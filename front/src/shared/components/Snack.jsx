import {Alert, Snackbar} from "@mui/material";

const Snack = ({isOpen, onClose = Function.prototype}) => {
    return (
        <Snackbar
            open={isOpen}
            onClose={onClose}
            autoHideDuration={5000}
        >
            <Alert
                severity="success"
                variant="filled"
            >
                Товар добавлен в корзину
            </Alert>
        </Snackbar>
    );
};

export default Snack;