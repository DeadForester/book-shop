import { Paper } from '@mui/material';

const Chart = ({url, title}) => {
    return (
        <Paper variant="outlined" sx={{ p: 2 }}>
            <iframe src={url} width="100%" height="300" title={title} style={{ border: 'none' }} />
        </Paper>
    );
};

export default Chart;