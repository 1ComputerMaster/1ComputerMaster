import { apiInstance } from "./index.js";

const api = apiInstance();

async function coordinate(params, success, fail) {
  await api.get(`/map/coordinate`, { params: params }).then(success).catch(fail);
}

export { coordinate };
