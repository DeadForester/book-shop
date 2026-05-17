import {Box, Tab, Tabs} from "@mui/material";
import CurrencyRubleIcon from '@mui/icons-material/CurrencyRuble';
import BarChartIcon from '@mui/icons-material/BarChart';
import ShowChartIcon from '@mui/icons-material/ShowChart';
import {useState} from "react";

const Dashboard = () => {
    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{
                borderBottom: 1,
                borderColor: 'divider'
            }}>
                <Tabs value={value}
                      onChange={handleChange}
                      aria-label="dashboard tabs"
                      variant="fullWidth"
                >
                    <Tab icon={<CurrencyRubleIcon />} label="Финансы" />
                    <Tab icon={<BarChartIcon />} label="Популярность" />
                    <Tab icon={<ShowChartIcon />} label="Окупаемость" />
                </Tabs>

                {/*
                  <TabPanel value={value} index={0}>
                    <RecentsTab />
                  </TabPanel>

                  <TabPanel value={value} index={1}>
                    <FavoritesTab />
                  </TabPanel>

                  <TabPanel value={value} index={2}>
                    <NearbyTab />
                  </TabPanel>
                */}
            </Box>
        </Box>
    );
};

export default Dashboard;