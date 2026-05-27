import { Box, Typography, Card, CardContent, Divider, List, ListItem, ListItemText } from '@mui/material';


const InfoSection = ({ title, icon, items }) => {
    return (
        <Card elevation={2} sx={{ height: '100%' }}>
            <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 2 }}>
                    {icon}
                    <Typography variant="h6" fontWeight="600">{title}</Typography>
                </Box>
                <Divider sx={{ mb: 2 }} />
                <List dense disablePadding>
                    {items.map((item, idx) => (
                        <ListItem key={idx} sx={{ px: 0, py: 0.75 }}>
                            <ListItemText
                                primary={<Typography variant="body2" color="text.secondary">{item.label}</Typography>}
                                secondary={<Typography variant="body1" fontWeight="500">{item.value}</Typography>}
                            />
                        </ListItem>
                    ))}
                </List>
            </CardContent>
        </Card>
    );
};

export default InfoSection;