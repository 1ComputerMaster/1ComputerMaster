import { apiInstance } from "./index.js";

const api = apiInstance();

function listDong(param, success, fail) {
  api.get(`/dong`, { params: param }).then(success).catch(fail); //dongcode join houseinfo join baseaddress 백앤드에서 많이 불러와야함
}

function listSidoGuDong(param, success, fail) {
  api.get(`/sidoGuDong`, { params: param }).then(success).catch(fail); //dongcode join houseinfo join baseaddress 백앤드에서 많이 불러와야함
}

function listMyLike(param, success, fail) {
  api.get(`/likedeal`, { params: param }).then(success).catch(fail); //memberNum likedeal join with (no) housedeal join houseinfo 백앤드에서 많이 불러와야함
  //aptName,lat,lng,dongCode,dealYear,dealMonth,dealDay,area,dealAmount
}

async function deleteMyLike(param, success, fail) {
  await api.delete(`/likedeal`, { params: param }).then(success).catch(fail); //memberNum likedeal join with (no) housedeal join houseinfo 백앤드에서 많이 불러와야함
  //aptName,lat,lng,dongCode,dealYear,dealMonth,dealDay,area,dealAmount
}

async function insertMyLike(params, success, fail) {
  await api.post(`/likedeal`, JSON.stringify(params)).then(success).catch(fail); //memberNum likedeal join with (no) housedeal join houseinfo 백앤드에서 많이 불러와야함
  //aptName,lat,lng,dongCode,dealYear,dealMonth,dealDay,area,dealAmount
}

function HighId(success, fail) {
  api.get(`/likedeal/high`).then(success).catch(fail);
}

function listInterest(param, success, fail) {
  api.get(`/interest`, { params: param }).then(success).catch(fail); //memberNum join with (memberNum) interestedarea join with (dongcode) houseinfo join with (dongcode) housedeal 백앤드에서 많이 불러와야함
  //aptName,lat,lng,dongCode,dealYear,dealMonth,dealDay,area,dealAmount
}

async function insertInterest(param, success, fail) {
  await api.post(`/interest`, JSON.stringify(param)).then(success).catch(fail); //memberNum likedeal join with (no) housedeal join houseinfo 백앤드에서 많이 불러와야함
  //aptName,lat,lng,dongCode,dealYear,dealMonth,dealDay,area,dealAmount
}
async function deleteInterest(param, success, fail) {
  await api.delete(`/interest`, { params: param }).then(success).catch(fail); //memberNum likedeal join with (no) housedeal join houseinfo 백앤드에서 많이 불러와야함
  //aptName,lat,lng,dongCode,dealYear,dealMonth,dealDay,area,dealAmount
}
function getNews(param, success, fail) {
  api
    .get(
      `http://apis.data.go.kr/B490007/worldjob6/openApi6
?
`,
      { params: param }
    )
    .then(success)
    .catch(fail);
}

export { listDong, listMyLike, listInterest, listSidoGuDong, insertMyLike, HighId, deleteMyLike, deleteInterest, getNews, insertInterest };
