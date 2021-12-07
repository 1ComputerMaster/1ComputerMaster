<template>
  <v-container>
    <v-card>
      <v-card-title class="text-center justify-center py-6">
        <h1 class="font-weight-bold text-h2 basil--text">My Info</h1>
      </v-card-title>
      <v-form>
        <v-container>
          <v-text-field v-model="memberInfo.memberId" label="아이디" disabled></v-text-field>
          <v-text-field size="lg" v-model="memberInfo.memberName"></v-text-field>
          <v-text-field size="lg" v-model="memberInfo.memberEmail"></v-text-field>
          <v-row>
            <v-col align="right">
              <v-btn href="#" size="lg" class="mr-2"> <router-link :to="{ name: 'Home' }"> 취소</router-link></v-btn>
              <v-btn href="#" size="lg" class="ml-2" @click="modifyBtn">확인</v-btn>
            </v-col>
          </v-row>
          <v-row>
            <v-col align="right">
              <v-btn size="lg" href="#">회원탈퇴</v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-form>
    </v-card>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { mapMutations } from "vuex";

const memberStore = "memberStore";
const titleStore = "titleStore";

export default {
  name: "MemberInfo",
  data() {
    return {};
  },
  created() {
    this.SET_TITLE(6);
  },
  components: {},
  computed: {
    ...mapState(memberStore, ["isLogin", "memberInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["modifyMember"]),
    ...mapMutations(titleStore, ["SET_TITLE"]),
    async modifyBtn() {
      console.log("modifyMember");
      if (this.memberInfo) {
        await this.modifyMember(this.memberInfo);
        this.$router.push({ name: "Home" });
      }
    },
  },
};
</script>

<style></style>
