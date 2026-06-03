import { Paper, Typography } from '@mui/material';

const Chart = ({url, title, shortTitle = ''}) => {
    return (
        <Paper variant="outlined" sx={{ p: 2 }}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                {title}
            </Typography>
            <iframe src={url} width="100%" height="300" title={shortTitle} />
        </Paper>
    );
};

export default Chart;