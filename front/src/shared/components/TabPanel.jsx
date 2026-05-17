import { Box } from '@mui/material';

const TabPanel = (props) => {
    const { children, value, index, ...rest } = props;

    return (
        <Box
            role="tabpanel"
            hidden={value !== index}
            id={`tab-panel-${index}`}
            aria-labelledby={`tab-${index}`}
            sx={{ p: 2 }}
            {...rest}
        >
            {value === index && children}
        </Box>
    );
};

export default TabPanel;