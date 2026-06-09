import {
    Box,
    Card,
    CardContent,
    Divider,
    List,
    ListItem,
    ListItemText,
    Typography,
} from '@mui/material';
import { ReactNode } from 'react';

import { FieldsItem } from '@/components/profile-page/types.ts';

interface InfoSectionProps {
    title: string;
    icon: ReactNode;
    fields: FieldsItem[];
}

const InfoSection = ({ title, icon, fields }: InfoSectionProps) => {
    return (
        <Card elevation={2} sx={{ height: '100%' }}>
            <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 2 }}>
                    {icon}
                    <Typography variant="h6" sx={{ fontWeight: 600 }}>
                        {title}
                    </Typography>
                </Box>
                <Divider sx={{ mb: 2 }} />
                <List dense disablePadding>
                    {fields.map((item, idx) => (
                        <ListItem key={idx} sx={{ px: 0, py: 0.75 }}>
                            <ListItemText
                                primary={
                                    <Typography variant="body2" color="text.secondary">
                                        {item.label}
                                    </Typography>
                                }
                                secondary={
                                    <Typography variant="body1" sx={{ fontWeight: 500 }}>
                                        {item.value}
                                    </Typography>
                                }
                            />
                        </ListItem>
                    ))}
                </List>
            </CardContent>
        </Card>
    );
};

export default InfoSection;
