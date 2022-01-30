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
        console.log("currentUser:", currentUser);
        console.log("try to use: currentUser.username, currentUser.id, currentUser.roles");

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
                        {currentUser.password}
                    </p>
                    <p>
                        <strong>Id:</strong>{" "}
                        {currentUser.id}
                    </p>
                    <strong>Authorities:</strong>
                    <ul>
                        {currentUser.roles &&
                            currentUser.roles.map((role, index) => <li key={index}>{role.name}</li>)}
                    </ul>
                </div>
            );
        } else {
            window.location = "/404"
        }
    }
}

export default Profile;
