import { Box, Container, Paper, Tab, Tabs, Typography } from '@mui/material';
import {useState} from "react";
import TabPanel from '../shared/components/TabPanel.jsx';
import { AttachMoney, MenuBook, TrendingUp } from '@mui/icons-material';
import { FinanceTab, AssortmentTab, ActivityTab } from '../components/dashboard-page';

const Dashboard = () => {
    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ backgroundColor: 'background.default', minHeight: '100vh', py: 4 }}>
            <Container maxWidth="xl">
                <Box sx={{ mb: 4 }}>
                    <Typography variant="h4" fontWeight="bold" gutterBottom>
                        Панель аналитики
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                        Статистика продаж, ассортимента и активности пользователей
                    </Typography>
                </Box>
                <Paper elevation={2} sx={{ borderRadius: 2, overflow: 'hidden' }}>
                    <Box
                        sx={{ borderBottom: 1, borderColor: 'divider', backgroundColor: 'grey.50' }}
                    >
                        <Tabs
                            value={value}
                            onChange={handleChange}
                            variant="scrollable"
                            scrollButtons="auto"
                            aria-label="Вкладки дашборда"
                            sx={{
                                '& .MuiTab-root': {
                                    minHeight: 56,
                                    textTransform: 'none',
                                    fontSize: '1rem',
                                    fontWeight: 500,
                                    px: 3,
                                },
                            }}
                        >
                            <Tab
                                icon={<AttachMoney />}
                                iconPosition="start"
                                label="Финансы"
                                id="dashboard-tab-0"
                                aria-controls="dashboard-tabpanel-0"
                            />
                            <Tab
                                icon={<MenuBook />}
                                iconPosition="start"
                                label="Ассортимент"
                                id="dashboard-tab-1"
                                aria-controls="dashboard-tabpanel-1"
                            />
                            <Tab
                                icon={<TrendingUp />}
                                iconPosition="start"
                                label="Активность и заказы"
                                id="dashboard-tab-2"
                                aria-controls="dashboard-tabpanel-2"
                            />
                        </Tabs>
                    </Box>

                    <Box sx={{ p: 3 }}>
                        <TabPanel value={value} index={0}>
                            <FinanceTab />
                        </TabPanel>
                        <TabPanel value={value} index={1}>
                            <AssortmentTab />
                        </TabPanel>
                        <TabPanel value={value} index={2}>
                            <ActivityTab />
                        </TabPanel>
                    </Box>
                </Paper>
            </Container>
        </Box>
    );
};

export default Dashboard;