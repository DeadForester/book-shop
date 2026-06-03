import { Box } from '@mui/material';
import Chart from './Chart.jsx';

const AssortmentTab = () => {
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Chart
                url={
                    'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-2'
                }
                title={'Статистика по всем книгам'}
            />

            <Chart
                url={
                    'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-3'
                }
                title={'Популярность жанров'}
            />

            <Chart
                url={
                    'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-5'
                }
                title={'Топ авторов по сумме продаж'}
            />

            <Chart
                url={
                    'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-6'
                }
                title={'Топ авторов по количеству проданных книг'}
            />
        </Box>
    );
};

export default AssortmentTab;