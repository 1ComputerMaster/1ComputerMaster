<template>
  <v-card min-height="600px">
    <v-form @submit="onSubmit">
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
  <!-- <v-row class="mb-1">
    <v-col style="text-align: left">
      <v-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="subject-group" label="제목:" label-for="subject" description="제목을 입력하세요.">
          <b-form-input id="subject" v-model="article.title" type="text" required placeholder="제목 입력..."></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea id="content" v-model="article.content" placeholder="내용 입력..." rows="10" max-rows="15"></b-form-textarea>
        </b-form-group>

        <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'">글작성</b-button>
        <b-button type="submit" variant="primary" class="m-1" v-else>글수정</b-button>
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </v-form>
    </v-col>
  </v-row> -->
</template>

<script>
import { writeArticle, getArticle, modifyArticle, HighId } from "@/api/notice.js";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "NoticeWriteForm",
  data() {
    return {
      article: {
        noticeId: 0,
        author: "",
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
        this.$route.params.noticeId,
        console.log(this.$route.params.noticeId),
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
      this.$router.push({ name: "NoticeList" });
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
      this.article.noticeId = 0;
      this.article.title = "";
      this.article.content = "";
      this.$router.push({ name: "NoticeList" });
    },
    registArticle() {
      HighId(
        ({ data }) => {
          console.log(data[0] + 1);
          this.article.noticeId = data[0] + 1;
        },
        (error) => {
          console.log(error);
        }
      ),
        writeArticle(
          {
            noticeId: this.article.noticeId,
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
          noticeId: this.$route.params.noticeId,
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
          this.$router.push({ name: "NoticeList" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "NoticeList" });
    },
  },
  computed: {
    ...mapState(memberStore, ["memberInfo"]),
  },
};
</script>

<style></style>
