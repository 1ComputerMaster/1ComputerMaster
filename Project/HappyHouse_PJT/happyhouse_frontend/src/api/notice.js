import { apiInstance } from "./index.js";

const api = apiInstance();

function listArticle(param, success, fail) {
  api.get(`/notice/all`, { params: param }).then(success).catch(fail);
}
function HighId(success, fail) {
  api.get(`/notice/high`).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  api.post(`/notice`, JSON.stringify(article)).then(success).catch(fail);
}

function getArticle(noticeid, success, fail) {
  api.get(`/notice/${noticeid}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.put(`/notice`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(noticeid, success, fail) {
  api.delete(`/notice/${noticeid}`).then(success).catch(fail);
}

export { listArticle, writeArticle, getArticle, modifyArticle, deleteArticle, HighId };
