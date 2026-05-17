import {Box, Tab, Tabs} from "@mui/material";
import PhoneIcon from '@mui/icons-material/Phone';
import FavoriteIcon from '@mui/icons-material/Favorite';
import PersonPinIcon from '@mui/icons-material/PersonPin';
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
                    <Tab icon={<PhoneIcon />} label="RECENTS" />
                    <Tab icon={<FavoriteIcon />} label="FAVORITES" />
                    <Tab icon={<PersonPinIcon />} label="NEARBY" />
                </Tabs>
            </Box>
        </Box>
    );
};

export default Dashboard;