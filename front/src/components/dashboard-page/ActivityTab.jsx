import { Box } from '@mui/material';
import Chart from './Chart.jsx';

const ActivityTab = () => {
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Chart
                url={
                    'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-8'
                }
                title={'Воронка статусов заказов'}
            />

            <Chart
                url={
                    'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-7'
                }
                title={'Тепловая карта активности'}
            />
        </Box>
    );
};

export default ActivityTab;