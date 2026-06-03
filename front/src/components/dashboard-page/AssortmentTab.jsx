import { Box } from '@mui/material';
import Chart from './Chart.jsx';

const AssortmentTab = () => {
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Chart
                url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=1&orgId=1&theme=light'}
                title={'Статистика по всем книгам'}
                shortTitle={'Статистика книг'}
            />

            <Chart
                url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=2&orgId=1&theme=light'}
                title={'Популярность жанров'}
                shortTitle={'Популярность'}
            />

            <Chart
                url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=3&orgId=1&theme=light'}
                title={'Топ авторов по сумме продаж'}
                shortTitle={'Авторы по продажам'}
            />

            <Chart
                url={'http://localhost:3000/d-solo/your-dashboard-id?panelId=3&orgId=1&theme=light'}
                title={'Топ авторов по количеству проданных книг'}
                shortTitle={'Авторы по количеству книг'}
            />
        </Box>
    );
};

export default AssortmentTab;