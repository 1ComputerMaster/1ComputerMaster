<template>
  <v-card class="mx-auto">
    <v-divider></v-divider>
    <v-card-title class="text-left mx-4" style="font-size: 2em">{{ article.title }}</v-card-title>
    <v-card-text>
      <v-row>
        <v-col md="2" class="text-left mx-4" style="font-size: 1.5em">{{ article.author }} </v-col>
        <v-spacer></v-spacer>
        <v-col md="2" class="text-right mx-4">{{ article.date }}</v-col>
      </v-row>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-text class="text-left" style="min-height: 400px">
      {{ message }}
    </v-card-text>
    <v-card-actions>
      <v-btn @click="listArticle">목록</v-btn>
      <v-spacer></v-spacer>
      <v-btn v-if="ok" @click="moveModifyArticle" class="mr-2">수정</v-btn>
      <v-btn v-if="ok" @click="removeArticle">삭제</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
// import moment from "moment";
import { getArticle, deleteArticle } from "@/api/notice.js";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  data() {
    return {
      ok: false,
      article: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "memberInfo"]),
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },
    // changeDateFormat() {
    //   return moment(new Date(this.article.regtime)).format(
    //     "YYYY.MM.DD hh:mm:ss"
    //   );
    // },
  },
  created() {
    getArticle(
      this.$route.params.noticeId,
      (response) => {
        this.article = response.data;
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
    if (this.memberInfo.memberId == "admin") {
      this.ok = true;
    } else {
      this.ok = false;
    }
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "NoticeList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "NoticeModify",
        params: { noticeId: this.article.noticeId },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    removeArticle() {
      if (confirm("정말로 삭제?")) {
        deleteArticle(this.article.noticeId, () => {
          this.$router.push({ name: "NoticeList" });
        });
      }
    },
  },
};
</script>

<style></style>
