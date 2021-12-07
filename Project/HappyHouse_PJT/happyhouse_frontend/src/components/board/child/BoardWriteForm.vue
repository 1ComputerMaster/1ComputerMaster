<template>
  <v-card min-height="600px">
    <v-form @submit="onSubmit" @reset="onReset">
      <v-container>
        <v-text-field required clearable clear-icon="mdi-close-circle" v-model="article.title" label="제목"></v-text-field>
        <v-textarea rows="20" outlined clearable clear-icon="mdi-close-circle" required v-model="article.content" label="내용"></v-textarea>
        <v-row>
          <v-col color="2" align="left">
            <v-btn @click="listArticle">목록</v-btn>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="3" align="right">
            <v-btn type="reset" class="m-1" @click="clear">초기화</v-btn>
            <v-btn type="submit" class="m-1" v-if="this.type === 'register'">글작성</v-btn>
            <v-btn type="submit" class="m-1" v-else>글수정</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </v-card>
</template>

<script>
import { writeArticle, getArticle, modifyArticle } from "@/api/board.js";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "BoardWriteForm",
  data() {
    return {
      article: {
        boardId: 0,
        memberId: "",
        title: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      getArticle(
        this.$route.params.boardId,
        ({ data }) => {
          // this.article.articleno = data.article.articleno;
          // this.article.userid = data.article.userid;
          // this.article.subject = data.article.subject;
          // this.article.content = data.article.content;
          this.article = data;
        },
        (error) => {
          console.log(error);
        }
      );
      this.isUserid = true;
    }
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "BoardList" });
    },
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      // !this.article.userid && ((msg = "작성자 입력해주세요"), (err = false), this.$refs.userid.focus());
      err && !this.article.title && ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err && !this.article.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "register" ? this.registArticle() : this.updateArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.boardId = 0;
      this.article.title = "";
      this.article.content = "";
      this.$router.push({ name: "BoardList" });
    },
    registArticle() {
      // HighId(
      //   ({ data }) => {
      //     console.log(data[0] + 1);
      //     this.article.boardId = data[0] + 1;
      //   },
      //   (error) => {
      //     console.log(error);
      //   }
      // ),
      writeArticle(
        {
          boardId: this.article.boardId,
          memberId: this.memberInfo.memberId,
          author: this.memberInfo.memberId,
          title: this.article.title,
          content: this.article.content,
        },
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },

    updateArticle() {
      modifyArticle(
        {
          boardId: this.$route.params.boardId,
          author: this.memberInfo.memberId,
          title: this.article.title,
          content: this.article.content,
        },
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "BoardList" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
  },
  computed: {
    ...mapState(memberStore, ["memberInfo"]),
  },
};
</script>

<style></style>
