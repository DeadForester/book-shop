import { Box } from '@mui/material';
import Chart from './Chart.jsx';

const ActivityTab = () => {
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Chart
                url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=1&orgId=1&theme=light'}
                title={'Воронка статусов заказов'}
                shortTitle={'Статусы заказов'}
            />

            <Chart
                url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=2&orgId=1&theme=light'}
                title={'Тепловая карта активности'}
                shortTitle={'Карта активности'}
            />
        </Box>
    );
};

export default ActivityTab;