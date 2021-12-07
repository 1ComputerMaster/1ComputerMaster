<template>
  <v-container>
    <v-form>
      <v-row>
        <v-col></v-col>
        <v-col>
          <v-card width="600">
            <v-container>
              <v-alert show v-if="isLoginError">아이디 또는 비밀번호를 확인하세요.</v-alert>
              <v-text-field v-model="member.memberId" label="아이디" required></v-text-field>
              <v-text-field v-model="member.memberPw" type="password" label="비밀번호" required></v-text-field>
              <v-checkbox :label="`아이디 저장`"></v-checkbox>
              <div class="mt-5 mb-4 text-center">
                <v-btn type="button" size="lg" class="mb-3" @click="confirm" block>로그인</v-btn>
                <div class="mt-6">Don't have an account? <router-link :to="{ name: 'SignUp' }" class="link">Sign up</router-link></div>
              </div>
              <hr />
              <div class="mt-4">
                <v-btn type="button" variant="outline-secondary" size="lg" class="m-1" block>Connect with Kakao</v-btn>
              </div>
            </v-container>
          </v-card>
        </v-col>
        <v-col></v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { mapMutations } from "vuex";

const memberStore = "memberStore";
const titleStore = "titleStore";

export default {
  name: "MemberLogin",
  data() {
    return {
      member: {
        memberId: null,
        memberPw: null,
      },
    };
  },
  created() {
    this.SET_TITLE(5);
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["memberConfirm", "getMemberInfo"]),
    ...mapMutations(titleStore, ["SET_TITLE"]),
    async confirm() {
      await this.memberConfirm(this.member);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getMemberInfo(token);
        this.$router.push({ name: "Home" });
      }
    },
    movePage() {
      this.$router.push({ name: "SignUp" });
    },
  },
};
</script>

<style></style>
