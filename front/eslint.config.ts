// For more info, see https://github.com/storybookjs/eslint-plugin-storybook#configuration-flat-config-format
import storybook from "eslint-plugin-storybook";

import js from '@eslint/js';
import { defineConfig, globalIgnores } from 'eslint/config';
import reactHooks from 'eslint-plugin-react-hooks';
import reactRefresh from 'eslint-plugin-react-refresh';
import simpleImportSort from 'eslint-plugin-simple-import-sort';
import globals from 'globals';
import tseslint from 'typescript-eslint';

export default defineConfig([globalIgnores(['dist']), {
    files: ['**/*.{ts,tsx}'],
    extends: [
        js.configs.recommended,
        tseslint.configs.recommended,
        reactHooks.configs.flat.recommended,
        reactRefresh.configs.vite,
    ],
    plugins: {
        'simple-import-sort': simpleImportSort,
    },
    languageOptions: {
        ecmaVersion: 2020,
        globals: globals.browser,
    },
    rules: {
        'simple-import-sort/imports': 'error',
        'simple-import-sort/exports': 'error',
    },
}, ...storybook.configs["flat/recommended"]]);
