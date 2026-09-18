import type { Meta, StoryObj } from '@storybook/react-vite';
import { useState } from 'react';

import Snack from './Snack';

const meta: Meta<typeof Snack> = {
    title: 'Shared/Snack',
    component: Snack,
    tags: ['autodocs'],
    decorators: [
        (Story) => (
            <div style={{ minHeight: '100px', position: 'relative' }}>
                <Story />
            </div>
        ),
    ],
    args: {
        title: 'Операция выполнена успешно',
        severity: 'success',
    },
    render: function Render(args) {
        const [isOpen, setIsOpen] = useState(true);

        return (
            <Snack
                {...args}
                isOpen={isOpen ?? true}
                onClose={() => setIsOpen(false)}
                duration={99999}
            />
        );
    },
};

export default meta;
type Story = StoryObj<typeof Snack>;

export const Success: Story = {};

export const Info: Story = {
    args: {
        severity: 'info',
        title: 'Новая версия доступна',
    },
};

export const Warning: Story = {
    args: {
        severity: 'warning',
        title: 'Проверьте введённые данные',
    },
};

export const Error: Story = {
    args: {
        severity: 'error',
        title: 'Не удалось сохранить изменения',
    },
};
