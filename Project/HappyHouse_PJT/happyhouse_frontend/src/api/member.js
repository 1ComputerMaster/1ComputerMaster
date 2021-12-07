import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(member, success, fail) {
  await api.post(`/member/login`, JSON.stringify(member)).then(success).catch(fail);
}

async function findById(memberId, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/member/info/${memberId}`).then(success).catch(fail);
}

async function register(member, success, fail) {
  await api.post(`/member/register`, JSON.stringify(member)).then(success).catch(fail);
}
// function logout(success, fail)

async function modify(member, success, fail) {
  await api.put(`/member/modify`, JSON.stringify(member)).then(success).catch(fail);
}

export { login, findById, register, modify };
