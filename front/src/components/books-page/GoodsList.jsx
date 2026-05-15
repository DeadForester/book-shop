import GoodsItem from './GoodsItem.jsx';
import {Grid} from "@mui/material";

const GoodsList = (props) => {
    const { goods, onItemAdded } = props;

    return (
        <Grid container spacing={2}>
            {goods.map((item) => (
                <GoodsItem key={item.id} {...item} onItemAdded={onItemAdded} />
            ))}
        </Grid>
    );
};

export default GoodsList;