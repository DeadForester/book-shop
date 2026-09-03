import { expect, test } from '@playwright/test';

test.describe('Login', () => {
    test.beforeEach(async ({ page }) => {
        await page.goto('/login');

        await page.waitForTimeout(500);
    });

    test('login success', async ({ page }) => {
        expect(await page.title()).toEqual('Прочитайка - Вход');

        const loginField = page.getByTestId('login-field');
        const passwordField = page.getByTestId('password-field');
        const loginButton = page.getByTestId('login-button');

        await expect(loginField).toBeVisible();

        await expect(passwordField).toBeVisible();

        await expect(loginButton).toBeVisible();

        await loginField.fill('user@gmail.com');

        await passwordField.fill('12345678');

        await loginButton.click();

        await expect(page.getByTestId('login-result')).toContainText('Успешный вход.');
    });

    test('login denied', async ({ page }) => {
        expect(await page.title()).toEqual('Прочитайка - Вход');

        const loginField = page.getByTestId('login-field');
        const passwordField = page.getByTestId('password-field');
        const loginButton = page.getByTestId('login-button');

        await expect(loginField).toBeVisible();

        await expect(passwordField).toBeVisible();

        await expect(loginButton).toBeVisible();

        await loginField.fill('user@gmail.com');

        await passwordField.fill('123456789');

        await loginButton.click();

        await expect(page.getByTestId('login-result')).toContainText(
            'Не верные данные пользователя.'
        );

        await page.waitForTimeout(1000);

        expect(await page.title()).toEqual('Прочитайка - Вход');
    });
});
