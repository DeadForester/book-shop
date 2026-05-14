import GoodsItem from './GoodsItem';
import {Grid} from "@mui/material";

const GoodsList = (props) => {
    const { goods } = props;

    return (
        <Grid container spacing={2}>
            {goods.map((item) => (
                <GoodsItem key={item.id} {...item} />
            ))}
        </Grid>
    );
};

export default GoodsList;