import {Button, Card, CardActions, CardContent, CardMedia, Grid, Typography} from "@mui/material";
import {useBasketContext} from "../../hooks/useBasketContext.js";
import {useNavigate} from "react-router-dom";

const GoodsItem = ({ id, name, price, poster, onItemAdded }) => {

    const {addToOrder} = useBasketContext();

    const router = useNavigate();

    const handleAddToOrder = () => {
        addToOrder({ id, name, price});
        onItemAdded?.();
    };

    return (
        <Grid size={{ xs: 12, md: 4}}>
            <Card
                sx={{
                    height: '100%',
                    cursor: 'pointer'
            }}
                onClick={() => router(`/books/${id}`)}
            >
                <CardMedia
                    image={poster}
                    sx={{height: '140px'}}
                    title={name}
                    alt={name}
                />
                <CardContent>
                    <Typography
                        variant="h6"
                        component="h3"
                    >
                        {name}
                    </Typography>
                    <Typography
                        variant="body1"
                    >
                        Цена: {price} руб.
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button
                        variant="outlined"
                        onClick={handleAddToOrder}
                    >
                        Купить
                    </Button>
                </CardActions>
            </Card>
        </Grid>
    );
};

export default GoodsItem;