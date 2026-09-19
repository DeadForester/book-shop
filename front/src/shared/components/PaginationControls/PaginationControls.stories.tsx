import type { Meta, StoryObj } from '@storybook/react-vite';
import { useArgs } from 'storybook/preview-api';

import PaginationControls from './PaginationControls';

const meta: Meta<typeof PaginationControls> = {
    title: 'Shared/PaginationControls',
    component: PaginationControls,
    tags: ['autodocs'],
    args: {
        page: 1,
        totalPages: 10,
    },
    render: function Render(args) {
        const [{ page }, updateArgs] = useArgs();

        return (
            <PaginationControls
                {...args}
                page={page}
                onPageChange={(_event, newPage) => updateArgs({ page: newPage })}
            />
        );
    },
};

export default meta;
type Story = StoryObj<typeof PaginationControls>;

export const Default: Story = {};

export const FewPages: Story = {
    args: {
        totalPages: 3,
    },
};

export const ManyPages: Story = {
    args: {
        page: 15,
        totalPages: 30,
    },
};

export const SinglePage: Story = {
    args: {
        page: 1,
        totalPages: 1,
    },
};
