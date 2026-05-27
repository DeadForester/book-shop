import { Box, Card, CardContent, Chip, Typography } from '@mui/material';
import { WarningAmber } from '@mui/icons-material';

const DevPlaceholder = ({ title, icon }) => {
    return (
        <Card
            elevation={2}
            sx={{
                height: '100%',
                bgcolor: 'grey.50',
                border: '1px dashed',
                borderColor: 'grey.300',
            }}
        >
            <CardContent sx={{ textAlign: 'center', py: 4 }}>
                <Box sx={{ color: 'grey.500', mb: 1 }}>{icon}</Box>
                <Typography variant="h6" color="text.secondary" gutterBottom>
                    {title}
                </Typography>
                <Chip
                    icon={<WarningAmber />}
                    label="В разработке"
                    color="warning"
                    variant="outlined"
                    size="small"
                />
            </CardContent>
        </Card>
    );
};

export default DevPlaceholder;
