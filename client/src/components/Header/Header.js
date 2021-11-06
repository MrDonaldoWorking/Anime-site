import React from 'react';
import './Header.css';
import { Link } from "react-router-dom";

function Header() {
    return (
        <header className="App-header">
            <Link to="/" className="Home-button">&#8962;</Link>
        </header>
    );
}

export default Header;
