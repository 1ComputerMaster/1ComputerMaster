<template>
  <v-container>
    <v-form @submit="join" v-model="valid">
      <v-card class="m-3">
        <v-container>
          <v-text-field v-model="memberName" label="이름" required></v-text-field>
          <v-text-field v-model="memberId" label="아이디" required></v-text-field>
          <v-text-field v-model="memberPw" type="password" label="비밀번호" required></v-text-field>
          <v-text-field v-model="memberPwCheck" type="password" label="비밀번호 확인" required></v-text-field>
          <v-text-field v-model="memberEmail" label="이메일" required></v-text-field>
          <div class="mt-4 mb-4">
            <b-btn size="lg" block pill type="submit">회원가입</b-btn>
          </div>
          <div class="mt-4">
            <b-btn size="lg" block pill type="button">Sign up with Kakao</b-btn>
          </div>
        </v-container>
      </v-card>
    </v-form>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { mapMutations } from "vuex";

const memberStore = "memberStore";
const titleStore = "titleStore";

export default {
  name: "MemberJoin",
  data() {
    return {
      valid: false,
      memberName: "",
      memberId: "",
      memberPw: "",
      memberPwCheck: "",
      memberEmail: "",
    };
  },
  created() {
    this.SET_TITLE(4);
  },
  computed: {
    ...mapState(memberStore, ["isRegister", "isRegisterError"]),
  },
  methods: {
    ...mapActions(memberStore, ["registerMember"]),
    ...mapMutations(titleStore, ["SET_TITLE"]),
    async join(event) {
      event.preventDefault();

      if (this.memberName === "") {
        alert("name no");
        return;
      }
      if (this.memberId === "") {
        alert("id no");
        return;
      }
      if (this.memberPw === "") {
        alert("pw no");
        return;
      }
      if (this.memberPw !== this.memberPwCheck) {
        alert("pw no");
        return;
      }
      if (this.memberEmail === "") {
        alert("email no");
        return;
      }

      console.log("register");
      await this.registerMember({
        memberName: this.memberName,
        memberId: this.memberId,
        memberPw: this.memberPw,
        memberEmail: this.memberEmail,
      });

      if (this.isRegister) {
        this.$router.push({ name: "Home" });
      }
    },
  },
};
</script>

<style></style>
