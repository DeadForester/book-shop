import type { Preview } from '@storybook/react-vite';
import { MemoryRouter } from 'react-router-dom';

const preview: Preview = {
    decorators: [
        (Story) => (
            <MemoryRouter>
                <Story />
            </MemoryRouter>
        ),
    ],
    parameters: {
        controls: {
            matchers: {
                color: /(background|color)$/i,
                date: /Date$/i,
            },
        },

        a11y: {
            test: 'todo',
        },
    },
};

export default preview;
