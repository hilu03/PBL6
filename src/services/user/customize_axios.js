import axios from "axios";

const instance = axios.create({
    // baseURL: 'http://localhost:8080',
    baseURL: 'https://pbl6-amls.onrender.com',
});

export default instance;