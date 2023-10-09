import axios from "axios";
var MAIN_API_URL =window.MAIN_API_URL;

const postCreateUser = (params) => {
  return axios.post(MAIN_API_URL + "/user", params);
}

const putUpdateUser = (userId,params) => {
  return axios.put(MAIN_API_URL + "/user/"+userId, params);
}

const postSearchUsers = (params) => {
  return axios.post(MAIN_API_URL + "/user/search", params);
}
const deleteUser = (userId) => {
  return axios.delete(MAIN_API_URL + "/user/"+userId);
}


const userServices={   
  postCreateUser,
  putUpdateUser,
  postSearchUsers,
  deleteUser
  
}
export default userServices;