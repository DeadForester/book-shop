import {Button, Card, CardActions, CardContent, CardMedia, Grid, Typography} from "@mui/material";
import {useBasketContext} from "../context/hooks/useBasketContext.js";

const GoodsItem = (props) => {
    const { name, price, poster } = props;

    const {addToOrder} = useBasketContext();

    return (
        <Grid size={{ xs: 12, md: 4}}>
            <Card
                sx={{
                    height: '100%',
                    cursor: 'pointer'
            }}
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
                        onClick={() =>
                            addToOrder({
                                id: props.id,
                                name: props.name,
                                price: props.price,
                            })
                        }
                    >
                        Купить
                    </Button>
                </CardActions>
            </Card>
        </Grid>
    );
};

export default GoodsItem;