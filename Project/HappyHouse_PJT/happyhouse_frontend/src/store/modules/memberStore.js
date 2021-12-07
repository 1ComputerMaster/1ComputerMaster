import jwt_decode from "jwt-decode";
import { login, findById, register, modify } from "@/api/member.js";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    isRegister: false,
    isRegisterError: false,
    memberInfo: null,
  },
  getters: {
    checkMemberInfo: function (state) {
      return state.memberInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_REGISTER: (state, isRegister) => {
      state.isRegister = isRegister;
    },
    SET_REGISTER_ERROR: (state, isRegisterError) => {
      state.isRegisterError = isRegisterError;
    },
    SET_MEMBER_INFO: (state, memberInfo) => {
      state.isLogin = true;
      state.memberInfo = memberInfo;
    },
  },
  actions: {
    async memberConfirm({ commit }, member) {
      await login(
        member,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    getMemberInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.memberId,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_MEMBER_INFO", response.data.memberInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    registerMember({ commit }, member) {
      register(
        member,
        () => {
          alert("success");
          commit("SET_REGISTER", true);
          commit("SET_REGISTER_ERROR", false);
        },
        (error) => {
          console.log(error);
          commit("SET_REGISTER", false);
          commit("SET_REGISTER_ERROR", true);
        }
      );
    },
    modifyMember({ commit }, member) {
      modify(
        member,
        () => {
          alert("success");
          commit("SET_MEMBER_INFO", member);
        },
        (error) => {
          console.log(error);
          alert("fail");
        }
      );
    },
  },
};

export default memberStore;
