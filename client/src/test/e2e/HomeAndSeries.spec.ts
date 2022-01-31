import { test, expect } from '@playwright/test';
import playwright, { chromium, Browser, Page } from 'playwright';

describe('Home and Series', () => {
    const browsers = ['chromium'/*, 'firefox', 'webkit'*/];

    let browser: Browser;
    let page: Page;

    beforeAll(async () => {
        jest.setTimeout(30000);
        browser = await chromium.launch({ headless: true });
        page = await browser.newPage();
    });

    it("basic check", async () => {
        await page.goto('http://localhost:3000');
        expect(await page.title()).toBe('React App');
    });

    it("go to undone page", async () => {
        await page.goto('http://localhost:3000');
        await page.click('.Login');
        expect(await page.locator('#root h1').innerText()).toMatch(/404/);
        
        await page.click('.Home-button');
        expect(await page.locator('#root h2').innerText()).toEqual("Featured:");
    });

    xit("Home snapshots", async() => {
        for (const browserType of browsers) {
            const browser = await playwright[browserType].launch({
                headless: false,
                slowMo: 100
            });
            const page = await browser.newPage();
            await page.goto('http://localhost:3000');
            await page.locator('.Login').hover();
            await page.screenshot({
                fullPage: true,
                path: `Home-${browserType}.png`
            });
            await browser.close();
        }
        for (const b1 of browsers) {
            const browser = await playwright[b1].launch();
            const page = await browser.newPage();
            await page.goto('http://localhost:3000');
            await page.locator('.Login').hover();
            for (const b2 of browsers) {
                expect(await page.screenshot({
                    fullPage: true
                })).toMatchSnapshot(`Home-${b2}.png`);
            }
        }
    });

    afterAll(async () => {
        await browser.close();
    });
});

// test('basic test', async ({ page }) => {
//     await page.goto('https://playwright.dev/');
//     const title = page.locator('.navbar__inner .navbar__title');
//     await expect(title).toHaveText('Playwright');
// });
