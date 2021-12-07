const boardStore = {
  namespaced: true,
  state: { boardId: 0 },
  getters: {},
  mutations: {
    SET_BOARD_ID: (state, boardId) => {
      state.boardId = boardId;
    },
  },
  actions: {
    modifyboardId({ commit }, boardId) {
      commit("SET_BOARD_ID", boardId);
    },
  },
};

export default boardStore;
