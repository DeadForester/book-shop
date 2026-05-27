import { Grid } from "@mui/material";
import HistoryItem from "./HistoryItem.jsx";

const HistoryList = ({orders}) => {

    return (
        <Grid container spacing={3} sx={{ mb: 4 }}>
            {orders.map((order) => (
                <HistoryItem key={order.id} {...order} />
            ))}
        </Grid>
    );
};

export default HistoryList;