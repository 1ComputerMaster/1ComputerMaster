<template>
  <div>
    <v-container fluid class="mx-auto" style="max-width: 70%">
      <v-toolbar extended rounded>
        <v-toolbar-title>
          <router-link :to="{ name: 'Home' }">
            <img src="@/assets/ssafy_logo.png" class="d-inline-block align-middle" width="90px" alt="Kitten" />
          </router-link>
        </v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>

        <div v-if="memberInfo">
          <v-menu bottom left v-model="menu" :close-on-content-click="false" :nudge-width="200" offset-x>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon color="yellow" v-bind="attrs" v-on="on">
                <v-avatar color="indigo">
                  <v-icon dark> mdi-account-circle </v-icon>
                </v-avatar>
              </v-btn>
            </template>
            <v-card class="justify-center text-center mx-auto text-xs-center">
              <v-list class="justify-center text-center mx-auto text-xs-center">
                <v-avatar color="indigo">
                  <v-icon dark> mdi-account-circle </v-icon>
                </v-avatar>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>{{ memberInfo.memberName }}</v-list-item-title>
                    <v-list-item-subtitle>{{ memberInfo.memberEmail }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <router-link :to="{ name: 'Info' }" class="link align-self-center">
                      <v-btn class="ma-2" outlined color="indigo" v-bind="attrs" v-on="on"> 내정보보기 </v-btn>
                    </router-link>
                  </template>
                  <span>내정보보기</span>
                </v-tooltip>
                <v-divider></v-divider>
                <v-btn class="m-2" outlined color="indigo" @click.prevent="onClickLogout">로그아웃</v-btn>
                <v-divider></v-divider>
              </v-list>
            </v-card>
          </v-menu>
        </div>
        <div v-else>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon large v-bind="attrs" v-on="on">
                <router-link :to="{ name: 'SignUp' }" class="link"><v-icon>mdi-account-circle</v-icon></router-link>
              </v-btn>
            </template>
            <span>SignUp</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon large v-bind="attrs" v-on="on">
                <router-link :to="{ name: 'Login' }" class="link"><v-icon>mdi-login</v-icon></router-link>
              </v-btn>
            </template>
            <span>로그인</span>
          </v-tooltip>
        </div>

        <template v-slot:extension>
          <v-tabs v-model="tab" centered>
            <v-tab><router-link :to="{ name: 'Notice' }" class="link"> 공지사항</router-link></v-tab>
            <v-tab><router-link :to="{ name: 'Board' }" class="link">게시판</router-link></v-tab>
            <v-tab><router-link :to="{ name: 'SearchDeal' }" class="link">거래 검색</router-link></v-tab>
          </v-tabs>
        </template>
      </v-toolbar>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";
const titleStore = "titleStore";

export default {
  name: "NaviBar",
  data() {
    return {
      show: false,
      menu: null,
      tab: 0,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "memberInfo"]),
  },
  watch: {
    tab(newTab) {
      this.SET_TITLE(newTab + 1);
    },
  },
  created() {},
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_MEMBER_INFO"]),
    ...mapMutations(titleStore, ["SET_TITLE"]),
    changeTitle() {},
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_MEMBER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style></style>
