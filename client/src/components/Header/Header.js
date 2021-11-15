import React, { Component } from "react";
import './Header.css';
import { Link } from "react-router-dom";

import EventBus from "../../payload/event-bus"
import SessionService from '../../service/session.service';

function CurrentUser(props) {
    if (props.user) {
        return <p className="User">{props.user.name}</p>;
    } else {
        return <p className="User">Guest</p>;
    }
}

function Enter(props) {
    if (props.user) {
        return (
            <div className="Enter">
                <Link to="/login">Sign out</Link>
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

class Header extends Component {
    constructor (props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            showAdminBoard: false,
            CurrentUser: undefined
        };
    }

    componentDidMount() {
        const user = SessionService.getCurrentUser();

        if (user) {
            this.setState({
                currentUser: user,
                showAdminBoard: user.roles.includes("ROLE_ADMIN")
            });
        }

        EventBus.on("logout", () => {
            this.logOut();
        });
    }

    componentWillUnmount() {
        EventBus.remove("logout");
    }

    logOut() {
        SessionService.logout();
        this.setState({
            showAdminBoard: false,
            currentUser: undefined
        });
    }

    render() {
        const { currentUser, showAdminBoard } = this.state;

        return (
            <header className="App-header">
                <CurrentUser user={currentUser} />
                <Link to="/" className="Home-button">
                    <span role="img" aria-label="home">&#127968;</span>
                </Link>
                <Enter user={currentUser} />
            </header>
        );
    }
}

export default Header;
