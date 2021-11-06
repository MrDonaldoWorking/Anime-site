import React from 'react';
import Footer from "../../components/Footer/Footer";
import {render, screen, waitFor} from "@testing-library/react";

describe("Footer", () => {
    it("contains author", () => {
        render(<Footer />);
        expect(screen.getByText("MrDonaldo")).toBeInTheDocument();
    });

    it("contains react logo", () => {
        render(<Footer />);
        expect(screen.getByRole('img')).not.toBeNull();
    });

    it("contains services list", async () => {
        global.fetch = jest.fn(() => 
            Promise.resolve({
                json: () => Promise.resolve({
                    streams: [
                        { href: 'https://vk.com', name: 'VK' },
                        { href: 'https://dailymotion.com', name: 'Dailymotion' },
                        { name: 'Empty', href: '' }
                    ],
                })
            })
        );

        render(<Footer />);
        await waitFor(() => screen.getByText("Dailymotion"));
        expect(screen.getByText("VK")).toBeInTheDocument();
        expect(screen.getAllByRole('listitem')).toHaveLength(3);
    });
});