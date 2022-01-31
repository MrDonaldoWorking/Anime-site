import {render, screen, waitFor} from "@testing-library/react";
import Header from '../../components/Header/Header';
import { BrowserRouter as Router } from 'react-router-dom';

describe("Header", () => {
    it("contains home button", () => {
        render(
            <Router>
                <Header />
            </Router>
        );

        // span -> img
        expect(screen.getByRole('img')).toBeInTheDocument();
    });

    it("contains Sign in/up and guest", () => {
        render(
            <Router>
                <Header />
            </Router>
        );

        expect(screen.getAllByText(/Sign/)).toHaveLength(2);
        expect(screen.getByText('Guest')).toBeInTheDocument();
    });
});