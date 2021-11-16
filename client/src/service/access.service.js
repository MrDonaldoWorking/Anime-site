import axios from 'axios';
import authHeader from '../payload/auth-header';

const API_URL = 'http://localhost:5000/spring/test/';

function request(str) {
    return axios.get(API_URL + str);
}

function requestWH(str) {
    return axios.get(API_URL + str, { headers: authHeader() });
}

class AccessService {
    getPublicContent() {
        return request('all');
    }

    getUserBoard() {
        return requestWH('user');
    }

    getAdminBoard() {
        return requestWH('admin');
    }

    getTitles() {
        return request('titles');
    }

    getSeriesDescription(id) {
        return request('series/' + id);
    }

    getStreams() {
        return request('streams');
    }
}

export default new AccessService();
