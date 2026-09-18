import { Construction, Engineering } from '@mui/icons-material';
import type { Meta, StoryObj } from '@storybook/react-vite';

import DevPlaceholder from './DevPlaceholder';

const meta: Meta<typeof DevPlaceholder> = {
    title: 'Shared/DevPlaceholder',
    component: DevPlaceholder,
    tags: ['autodocs'],
    args: {
        title: 'Находится в разработке',
        icon: <Construction fontSize="large" />,
    },
};

export default meta;
type Story = StoryObj<typeof DevPlaceholder>;

export const Default: Story = {};

export const CustomTitle: Story = {
    args: {
        title: 'Аналитика скоро появится',
    },
};

export const CustomIcon: Story = {
    args: {
        icon: <Engineering fontSize="large" />
    }
};