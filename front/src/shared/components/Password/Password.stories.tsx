import type { Meta, StoryObj } from '@storybook/react-vite';
import { useArgs } from 'storybook/preview-api';

import Password from './Password';

const meta: Meta<typeof Password> = {
    title: 'Shared/Password',
    component: Password,
    tags: ['autodocs'],
    args: {
        password: '',
        error: '',
        loading: false,
        label: 'Пароль',
        testId: 'password-input',
    },
    render: function Render(args) {
        const [{ password }, updateArgs] = useArgs();

        return (
            <Password
                {...args}
                password={password}
                setPassword={(newPassword) => updateArgs({ password: newPassword })}
            />
        );
    },
};

export default meta;
type Story = StoryObj<typeof Password>;

export const Default: Story = {};

export const WithError: Story = {
    args: {
        password: 'weak',
        error: 'Пароль слишком короткий',
    },
};

export const Loading: Story = {
    args: {
        password: '123456',
        loading: true,
    },
};

export const CustomLabel: Story = {
    args: {
        label: 'Придумайте пароль',
    },
};
