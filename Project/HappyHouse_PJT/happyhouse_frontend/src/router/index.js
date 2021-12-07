import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";

import Member from "@/views/Member.vue";
import MemberLogin from "@/components/user/MemberLogin.vue";
import MemberSignUp from "@/components/user/MemberSignUp.vue";
import MemberInfo from "@/components/user/MemberInfo.vue";

import Notice from "@/views/Notice.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import NoticeWrite from "@/components/notice/NoticeWrite.vue";
import NoticeDetail from "@/components/notice/NoticeDetail.vue";
import NoticeModify from "@/components/notice/NoticeModify.vue";

import Board from "@/views/Board.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import BoardModify from "@/components/board/BoardModify.vue";

import SearchDeal from "@/views/SearchDeal.vue";

import store from "@/store/index.js";

Vue.use(VueRouter);

// 로그인 확인 여부 함수
// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkMemberInfo = store.getters["memberStore/checkMemberInfo"];
  const getMemberInfo = store._actions["memberStore/getMemberInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkMemberInfo == null && token) {
    await getMemberInfo(token);
  }
  if (checkMemberInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "SignIn" });
    router.push({ name: "SignIn" });
  } else {
    console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/member",
    name: "Member",
    component: Member,
    children: [
      {
        path: "login",
        name: "Login",
        component: MemberLogin,
      },
      {
        path: "singup",
        name: "SignUp",
        component: MemberSignUp,
      },
      {
        path: "info",
        name: "Info",
        beforeEnter: onlyAuthUser,
        component: MemberInfo,
      },
    ],
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: NoticeList,
      },
      {
        path: "write",
        name: "NoticeWrite",
        beforeEnter: onlyAuthUser,
        component: NoticeWrite,
      },
      {
        path: "detail/:noticeid",
        name: "NoticeDetail",
        beforeEnter: onlyAuthUser,
        component: NoticeDetail,
      },
      {
        path: "modify/:noticeid",
        name: "NoticeModify",
        beforeEnter: onlyAuthUser,
        component: NoticeModify,
      },
    ],
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "BoardList",
        component: BoardList,
      },
      {
        path: "write",
        name: "BoardWrite",
        beforeEnter: onlyAuthUser,
        component: BoardWrite,
      },
      {
        path: "detail/:boardid",
        name: "BoardDetail",
        beforeEnter: onlyAuthUser,
        component: BoardDetail,
      },
      {
        path: "modify/:boardid",
        name: "BoardModify",
        beforeEnter: onlyAuthUser,
        component: BoardModify,
      },
    ],
  },
  {
    path: "/searchdeal",
    name: "SearchDeal",
    component: SearchDeal,
  },
  {
    path: "*",
    redirect: "/",
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
