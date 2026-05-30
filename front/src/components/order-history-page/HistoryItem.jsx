import {
    Box,
    Card,
    CardContent,
    CardHeader,
    Chip,
    Divider,
    Grid,
    List,
    ListItem,
    ListItemText,
    Typography
} from "@mui/material";
import {
    CalendarToday as DateIcon,
    Receipt as ReceiptIcon
} from '@mui/icons-material';


const HistoryItem = ({ id, date, items, total}) => {
    const formatCurrency = (amount) =>
        new Intl.NumberFormat('ru-RU', { style: 'currency', currency: 'RUB' }).format(amount);

    return (
        <Grid size={{ xs: 12, sm: 6, md: 4 }}>
            <Card
                sx={{
                    height: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    transition: 'transform 0.2s, box-shadow 0.2s',
                    '&:hover': {
                        transform: 'translateY(-4px)',
                        boxShadow: 6,
                    },
                }}
                elevation={2}
            >
                <CardHeader
                    title={
                        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                            <ReceiptIcon color="primary" fontSize="small" />
                            <Typography variant="subtitle1" fontWeight="bold">
                                #{id}
                            </Typography>
                        </Box>
                    }
                    subheader={
                        <Box sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
                            <DateIcon fontSize="small" sx={{ color: 'text.secondary' }} />
                            <Typography variant="body2" color="text.secondary">
                                {date}
                            </Typography>
                        </Box>
                    }
                    sx={{ pb: 1, borderBottom: 1, borderColor: 'divider' }}
                />

                <CardContent sx={{ flexGrow: 1 }}>
                    <Typography variant="subtitle2" gutterBottom color="text.secondary">
                        Товары в заказе:
                    </Typography>
                    <List dense sx={{ py: 0 }}>
                        {items.map((item, idx) => (
                            <ListItem
                                key={`${id}-${idx}`}
                                sx={{ px: 0, py: 0.5 }}
                                secondaryAction={
                                    <Chip
                                        label={`×${item.qty}`}
                                        size="small"
                                        variant="outlined"
                                        sx={{ ml: 1, height: 20 }}
                                    />
                                }
                            >
                                <ListItemText
                                    primary={
                                        <Typography variant="body2" component="span">
                                            {item.name}
                                        </Typography>
                                    }
                                />
                            </ListItem>
                        ))}
                    </List>
                </CardContent>

                <Divider />

                <Box sx={{ p: 2, textAlign: 'right', backgroundColor: 'grey.50' }}>
                    <Typography variant="h6" color="primary" fontWeight="bold">
                        {formatCurrency(total)}
                    </Typography>
                    <Typography variant="caption" color="text.secondary">
                        Общая сумма
                    </Typography>
                </Box>
            </Card>
        </Grid>
    );
};

export default HistoryItem;