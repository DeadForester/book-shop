import { expect, test } from '@playwright/test';

test.describe('Visual regression', () => {
    test.beforeEach(async ({ page }) => {
        await page.route('**/*', async (route) => {
            const request = route.request();
            const url = request.url();

            if (url.startsWith('http') && /\.(jpg|jpeg|png|webp|gif|svg)(\?.*)?$/i.test(url)) {
                await route.fulfill({
                    status: 200,
                    contentType: 'image/svg+xml',
                    body: `
                    <svg xmlns="http://www.w3.org/2000/svg"
                         width="200"
                         height="300">
                        <rect
                            width="200"
                            height="300"
                            fill="#ddd"
                        />
                    </svg>
                `,
                });

                return;
            }

            await route.continue();
        });
    });

    test('home page', async ({ page }) => {
        await page.goto('/');

        await expect(page).toHaveScreenshot('home.png', {
            fullPage: true,
        });
    });

    test('book page', async ({ page }) => {
        await page.goto('/books/01');

        await expect(page).toHaveScreenshot('book01.png', {
            fullPage: true,
        });
    });
});
