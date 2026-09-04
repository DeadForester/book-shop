import { expect, Locator, test } from '@playwright/test';

test.describe('Login', () => {
    let loginField: Locator;
    let passwordField: Locator;
    let loginButton: Locator;

    test.beforeEach(async ({ page }) => {
        await page.goto('/login');

        loginField = page.getByTestId('login-field');
        passwordField = page.getByTestId('password-field');
        loginButton = page.getByTestId('login-button');

        await expect(loginField).toBeVisible();
        await expect(passwordField).toBeVisible();
        await expect(loginButton).toBeVisible();
    });

    test('login success', async ({ page }) => {
        expect(await page.title()).toEqual('Прочитайка - Вход');

        await loginField.fill('user@gmail.com');

        await passwordField.fill('12345678');

        await loginButton.click();

        await expect(page.getByTestId('login-result')).toContainText('Успешный вход.');
    });

    test('login denied', async ({ page }) => {
        expect(await page.title()).toEqual('Прочитайка - Вход');

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
