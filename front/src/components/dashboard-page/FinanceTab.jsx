import { Box } from '@mui/material';
import Chart from './Chart.jsx';

const FinanceTab = () => (
    <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
        <Chart
            url={
                'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-1'
            }
            title={'Сумма всех заказов по дням'}
            shortTitle={'Сумма заказов'}
        />

        <Chart
            url={
                'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-4'
            }
            title={'Средний чек по часам'}
            shortTitle={'Средний чек'}
        />

        <Chart
            url={
                'http://localhost:3001/d-solo/adz5sdc/analitika-v-knizhnomu-magazinu?orgId=1&timezone=browser&var-query0=&theme=light&panelId=panel-9'
            }
            title={'Тренд среднего чека'}
            shortTitle={'Тренд'}
        />
    </Box>
);

export default FinanceTab;
