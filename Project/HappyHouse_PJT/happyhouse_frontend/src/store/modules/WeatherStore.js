import { getWeather } from "@/api/weather.js";

const WeatherStore = {
  namespaced: true,
  state: {
    weathers: [],
  },
  getters: {},
  mutations: {
    SET_WEATHERS: (state, items) => {
      state.weathers = items;
    },
  },
  actions: {
    getWeather: ({ commit }, params) => {
      getWeather(
        params,
        (response) => {
          console.log(response.data.response.body.items.item);
          commit("SET_WEATHERS", response.data.response.body.items.item);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default WeatherStore;
