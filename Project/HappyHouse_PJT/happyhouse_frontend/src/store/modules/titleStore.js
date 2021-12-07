const titleStore = {
  namespaced: true,
  state: {
    title: null,
    titleItems: ["", "공지사항", "게시판", "거래검색", "회원가입", "로그인", "회원정보"],
  },
  mutations: {
    SET_TITLE: (state, index) => {
      state.title = state.titleItems[index];
    },
  },
};

export default titleStore;
