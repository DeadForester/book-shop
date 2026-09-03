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
        const passwordField = page.getByTestId('password-field');
        const loginField = page.getByTestId('login-field');

        expect(passwordField).not.toBeNull();

        expect(loginField).not.toBeNull();

        await passwordField.fill('123456789');

        await loginField.fill('user@gmail.com');

        await page.getByTestId('login-button').click();

        await expect(page.getByTestId('login-result')).toContainText(
            'Не верные данные пользователя.'
        );

        await page.waitForTimeout(1000);

        expect(page.title()).toEqual('Прочитайка - Вход');
    });
});
