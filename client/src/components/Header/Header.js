import React, { useEffect, useState } from 'react';
import './Header.css';
import { Link } from "react-router-dom";

function CurrentUser(props) {
    if (props.user.id !== undefined) {
        return <p className="User">{props.user.name}</p>;
    } else {
        return <p className="User">Guest</p>;
    }
}

function Enter(props) {
    if (props.user.id !== undefined) {
        return (
            <div className="Enter">
                <Link>Sign out</Link>
            </div>
        );
    } else {
        return (
            <div className="Enter">
                <Link to="/login" className="Login">Sign in</Link>
                <Link to="/register" className="Register">Sign up</Link>
            </div>
        );
    }
}

function Header() {
    const [user, setUser] = useState({});

    useEffect(() => {
        const fetchUserData = async () => {
            fetch('/express/user')
                .then(res => res.json())
                .then(res => setUser(res));
        }
        fetchUserData();
    }, []);

    return (
        <header className="App-header">
            <CurrentUser user={user} />
            <Link to="/" className="Home-button">
                <span role="img" aria-label="home">&#127968;</span>
            </Link>
            <Enter user={user} />
        </header>
    );
}

export default Header;
