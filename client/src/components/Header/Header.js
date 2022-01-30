import React, { Component } from "react";
import './Header.css';
import { Link } from "react-router-dom";

import EventBus from "../../payload/event-bus"
import SessionService from '../../service/session.service';
// import AuthVerify from '../../payload/auth-verify'

function CurrentUser(props) {
    console.log("user is", props.user);
    if (props.user) {
        return (
            <div className="Profile">
                <Link to="/profile">{props.user.username}</Link>
            </div>
        );
    } else {
        return <p className="User">Guest</p>;
    }
}

function Enter(props) {
    if (props.user) {
        return (
            <div className="Enter">
                <Link to="/login" onClick={props.logOut}>Sign out</Link>
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
        if (!SessionService.logout()) {
            alert("User in front and back differs!");
        } else {
            this.setState({
                showAdminBoard: false,
                currentUser: undefined
            });
        }
    }

    render() {
        const { currentUser, showAdminBoard } = this.state;

        return (
            <>
                <header className="App-header">
                    <CurrentUser user={currentUser} />
                    <Link to="/" className="Home-button">
                        <span role="img" aria-label="home">&#127968;</span>
                    </Link>
                    <Enter user={currentUser} logOut={this.logOut} />
                </header>
                {/* <AuthVerify logOut={this.logOut} /> */}
            </>
        );
    }
}

export default Header;
