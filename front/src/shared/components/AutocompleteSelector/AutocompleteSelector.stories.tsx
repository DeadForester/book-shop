import type { Meta, StoryObj } from '@storybook/react-vite';
import { useArgs } from 'storybook/preview-api';

import AutocompleteSelector from './AutocompleteSelector';

const genreOptions = ['Фантастика', 'Детектив', 'Роман', 'Фэнтези', 'Поэзия', 'Биография'];

const meta: Meta<typeof AutocompleteSelector> = {
    title: 'Shared/AutocompleteSelector',
    component: AutocompleteSelector,
    tags: ['autodocs'],
    args: {
        value: null,
        options: genreOptions,
        label: 'Жанр',
        placeholder: 'Выберите жанр',
    },
    render: function Render(args) {
        const [{ value }, updateArgs] = useArgs();

        return (
            <AutocompleteSelector
                {...args}
                value={value}
                onChange={(_event, newValue) => updateArgs({ value: newValue })}
            />
        );
    },
};

export default meta;
type Story = StoryObj<typeof AutocompleteSelector>;

export const Default: Story = {};

export const WithSelectedValue: Story = {
    args: {
        value: 'Фантастика',
    },
};

export const EmptyOptions: Story = {
    args: {
        options: [],
        placeholder: 'Нет доступных вариантов',
    },
};
