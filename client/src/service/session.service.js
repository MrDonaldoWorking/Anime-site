import axios from "axios";

const API_URL = "http://localhost:5000/spring/auth/";

class SessionService {
    // POST {username, password}, save JWT on local storage
    login(username, password) {
        return axios
            .post(API_URL + "signin", {
                username,
                password
            })
            .then(response => {
                console.log("response:", response);
                console.log("data:", response.data);
                if (response.status === 200) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }
//                 if (response.data.accessToken) {
//                     localStorage.setItem("user", JSON.stringify(response.data));
//                 }

                return response.data;
            });
    }

    // remove JWT from local storage
    logout() {
        const user = this.getCurrentUser();
        if (user === null) {
            return true;
        }

        const username = user.username;
        let result;
        axios.post(API_URL + "logout", {username})
             .then(
                response => {
                    console.log("logout response:", response);
                    result = response;
                },
                error => {
                    console.log("logout error response:", error);
                    console.log("error.name:", error.name);
                    console.log("error.message:", error.message);
                    console.log("error.message === \"Network Error\":", error.message === "Network Error");
                    // when there is no connection
                    if (error.message === "Network Error") {
                        localStorage.removeItem('user');
                        return true;
                    }
                });
        if (result.status === 200) {
            localStorage.removeItem("user");
            return true;
        }
        return false;
    }

    // POST {username, password}
    register(username, password) {
        return axios.post(API_URL + "signup", {
            username,
            password
        });
    }

    // get stored user information with JWT
    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));;
    }
}

export default new SessionService();
