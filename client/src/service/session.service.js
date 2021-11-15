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
                if (response.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    // remove JWT from local storage
    logout() {
        localStorage.removeItem("user");
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
