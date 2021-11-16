import React, { Component } from "react";
import SessionService from "../../service/session.service";

class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: SessionService.getCurrentUser()
        };
    }

    render() {
        const { currentUser } = this.state;

        if (currentUser) {
            return (
                <div className="container">
                    <header className="jumbotron">
                        <h3>
                            <strong>{currentUser.username}</strong> Profile
                        </h3>
                    </header>
                    <p>
                        <strong>Token:</strong>{" "}
                        {currentUser.accessToken.substring(0, 20)} ...{" "}
                        {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}
                    </p>
                    <p>
                        <strong>Id:</strong>{" "}
                        {currentUser.id}
                    </p>
                    <strong>Authorities:</strong>
                    <ul>
                        {currentUser.roles &&
                            currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
                    </ul>
                </div>
            );
        } else {
            window.location = "/404"
        }
    }
}

export default Profile;
