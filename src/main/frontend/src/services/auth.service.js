import axios from "axios";
import TokenService from "./token.service";
const API_URL = "http://localhost:8080/api/v1/";

const register = (username, email, password) => {
  return axios.post(API_URL + "signup", {
    username,
    email,
    password
  });
};

const login = async (username, password) => {
  const response = await axios.post(API_URL + "signin", {
    username, password
  })
  .then((response) => {
    if(response.data.token) {
      TokenService.setUser(response.data);
    }
  });
  return response;
};

const logout = () => {
  TokenService.removeUser();
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  register,
  login,
  logout,
  getCurrentUser,
};
export default AuthService;