import { Box } from '@mui/material';
import Chart from './Chart.jsx';

const FinanceTab = () => (
    <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
        <Chart
            url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=1&orgId=1&theme=light'}
            title={'Сумма всех заказов по дням'}
            shortTitle={'Сумма заказов'}
        />

        <Chart
            url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=2&orgId=1&theme=light'}
            title={'Средний чек по часам'}
            shortTitle={'Средний чек'}
        />

        <Chart
            url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=3&orgId=1&theme=light'}
            title={'Тренд среднего чека'}
            shortTitle={'Тренд'}
        />
    </Box>
);

export default FinanceTab;
