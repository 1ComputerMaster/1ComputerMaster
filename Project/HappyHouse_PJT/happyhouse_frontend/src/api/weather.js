import { apiInstance } from "./index.js";

const api = apiInstance();

function getWeather(param, success, fail) {
  console.log();
  api.get(`/api/weather`, { params: param }).then(success).catch(fail);
}

export { getWeather };
