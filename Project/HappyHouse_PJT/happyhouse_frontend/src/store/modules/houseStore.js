import { sidoList, gugunList, dongList, houseList } from "@/api/house.js";
import { listDong, insertMyLike, listMyLike, deleteMyLike } from "@/api/searchdeal.js";
import { insertInterest, listInterest, deleteInterest } from "@/api/searchdeal.js";
import { coordinate } from "@/api/map.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    dongName: null,
    houses: [],
    house: null,
    mylikes: [],
    myInter: [],
    location: null,
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "선택하세요" }];
    },
    SET_DONG_NAME: (state, dongName) => {
      state.dongName = dongName;
    },
    SET_HOUSE_LIST: (state, houses) => {
      //   console.log(houses);
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_MOVE_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_MYLIKES: (state, mylikes) => {
      state.mylikes = mylikes;
    },
    SET_MYINTER: (state, myInter) => {
      state.myInter = myInter;
    },
    SET_LOCATION: (state, location) => {
      state.location = location;
    },
    SET_HOUSES_HOVER: (state) => {
      if (state.houses.length > 0) {
        for (let index = 0; index < state.houses.length; index++) {
          state.houses[index].isHovered = false;
        }
      }

      if (state.mylikes.length > 0) {
        for (let i = 0; i < state.mylikes.length; i++) {
          for (let j = 0; j < state.houses.length; j++) {
            if (state.mylikes[i].aptCode === state.houses[j].aptCode) {
              state.houses[j].isHovered = true;
            }
          }
        }
      }
    },
    SET_MYLIKES_HOVER: (state) => {
      if (state.mylikes.length > 0) {
        for (const key in state.mylikes) {
          state.mylikes[key].isHovered = true;
        }
      }
    },
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = {
        sido: sidoCode,
      };
      gugunList(
        params,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      const params = {
        gugun: gugunCode,
      };
      dongList(
        params,
        ({ data }) => {
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getCoordinate: ({ commit }, dongCode) => {
      const params = {
        dong: dongCode,
      };
      coordinate(
        params,
        (response) => {
          let location = {
            lat: response.data.lat,
            lng: response.data.lng,
          };
          commit("SET_LOCATION", location);
          commit("SET_DONG_NAME", response.data.dongName);
        },
        () => {}
      );
    },
    getHouseList: ({ commit }, dongCode) => {
      // vue cli enviroment variables 검색
      //.env.local file 생성.
      // 반드시 VUE_APP으로 시작해야 한다.
      // const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      //   const SERVICE_KEY =
      //     "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
      const params = {
        // LAWD_CD: dongCode,
        // DEAL_YMD: "202110",
        // serviceKey: decodeURIComponent(SERVICE_KEY),
        dong: dongCode,
      };
      houseList(
        params,
        (response) => {
          //console.log(response.data);
          commit("SET_HOUSE_LIST", response.data);
          commit("SET_HOUSES_HOVER");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
    searchHouseList: ({ commit }, dongName) => {
      const params = {
        // LAWD_CD: dongCode,
        // DEAL_YMD: "202110",
        // serviceKey: decodeURIComponent(SERVICE_KEY),
        dongName: dongName,
      };
      listDong(
        params,
        (response) => {
          console.log(response.data);
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setLikeDeal: async ({ commit }, params) => {
      await insertMyLike(
        params,
        () => {},
        (error) => {
          console.log(error);
        }
      );
      listMyLike(
        params,
        (response) => {
          commit("SET_MYLIKES", response.data);
          commit("SET_HOUSES_HOVER");
          commit("SET_MYLIKES_HOVER");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteLikeDeal: async ({ commit }, params) => {
      await deleteMyLike(
        params,
        () => {},
        (error) => {
          console.log(error);
        }
      );
      listMyLike(
        params,
        (response) => {
          commit("SET_MYLIKES", response.data);
          commit("SET_HOUSES_HOVER");
          commit("SET_MYLIKES_HOVER");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getInterest: ({ commit }, params) => {
      listInterest(
        params,
        (response) => {
          commit("SET_MYINTER", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setInterest: async ({ commit }, params) => {
      await insertInterest(
        params,
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
      listInterest(
        params,
        (response) => {
          commit("SET_MYINTER", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    removeInterest: async ({ commit }, params) => {
      await deleteInterest(
        params,
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
      listInterest(
        params,
        (response) => {
          commit("SET_MYINTER", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default houseStore;
