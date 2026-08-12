import { expect, test } from '@playwright/test';

test('login page', async ({ page }) => {
    await page.goto('http://localhost:3000/login');

    await expect(page).toHaveTitle(/Прочитайка - Вход/);
});
