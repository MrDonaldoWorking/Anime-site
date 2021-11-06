import { test, expect } from '@playwright/test';
const playwright = require('playwright');

const browsers = ['chromium', 'firefox', 'webkit'];

describe('Home ans Series', () => {
    it("Home snapshots", async() => {
        for (const browserType of browsers) {
            const browser = await playwright[browserType].launch();
            const context = await browser.newContext();
            const page = await context.newPage('http://localhost:3000');
            await page.locator('.Login').hover();
            await page.screenshot({
                fullPage: true,
                path: `Home-${browserType}.png`
            });
            await browser.close();
        }
        for (const b1 of browsers) {
            const browser = await playwright[b1].launch();
            const context = await browser.newContext();
            const page = await context.newPage('http://localhost:3000');
            await page.locator('.Login').hover();
            for (const b2 of browsers) {
                expect(await page.screenshot({
                    fullPage: true
                })).toMatchSnapshot(`Home-${b2}.png`);
            }
        }
    });
});

// test('basic test', async ({ page }) => {
//     await page.goto('https://playwright.dev/');
//     const title = page.locator('.navbar__inner .navbar__title');
//     await expect(title).toHaveText('Playwright');
// });