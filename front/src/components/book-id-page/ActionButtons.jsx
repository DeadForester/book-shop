import {Box, Button} from "@mui/material";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import {useNavigate} from "react-router-dom";


const ActionButtons = ({handleAddToCart}) => {
    const navigate = useNavigate();

    return (
        <Box sx={{ mt: 4, display: 'flex', flexWrap: 'wrap', gap: 2 }}>
            <Button
                variant="contained"
                size="large"
                startIcon={<ShoppingCartIcon />}
                onClick={handleAddToCart}
                sx={{ minWidth: 180 }}
            >
                В корзину
            </Button>
            <Button
                variant="outlined"
                size="large"
                onClick={() => navigate('/')}
            >
                Продолжить покупки
            </Button>
        </Box>
    );
};

export default ActionButtons;