import axios from "axios";
import TokenService from "./token.service";
import AuthService from "./auth.service";
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';

const instance = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
});
instance.interceptors.request.use(
  (config) => {
    const token = TokenService.getLocalAccessToken();
    if (token) {
      config.headers["Authorization"] = 'Bearer ' + token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
instance.interceptors.response.use(
  (res) => {
    return res;
  },
  async (err) => {
    const originalConfig = err.config;
    if (originalConfig.url !== "/signin" && err.response) {
      // Access Token was expired
      if (err.response.status === 401 && !originalConfig._retry) {
        originalConfig._retry = true;
        try {
          const rs = await instance.post("/refreshtoken", {
            refreshToken: TokenService.getLocalRefreshToken(),
          });
          const { accessToken } = rs.data;
          TokenService.updateLocalAccessToken(accessToken);
          return instance(originalConfig);
        } catch (_error) {
          if (err.response.status === 401 && _error.response.status === 500) {
            console.log("Refresh token was expired")
            toast.error("Refresh token was expired");
            AuthService.logout();
          }
          if (_error.response.status === 400) {
            console.log("Session expired, please login");
            toast.error("Session expired, please login");
          }
          return Promise.reject(_error);
        }
      }
    }
    return Promise.reject(err);
  }
);
export default instance;