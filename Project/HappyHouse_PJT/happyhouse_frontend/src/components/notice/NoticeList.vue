<template>
  <v-container>
    <p class="lead">공지사항을 확인하시고 사이트 내부 규칙을 알아보세요.</p>

    <div class="text-left">
      <small class="font-weight-bold">현 페이지 총 게시물 {{ articles.length }}건</small>
    </div>

    <v-row>
      <v-col>
        <v-data-table
          :headers="headers"
          :items="articles"
          :page.sync="page"
          :items-per-page="itemsPerPage"
          hide-default-footer
          class="elevation-1"
          @click:row="viewArticle"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>공지사항</v-toolbar-title>
              <v-divider class="mx-4" inset vertical></v-divider>
              <v-spacer></v-spacer>
              <v-btn v-if="ok" color="primary" dark class="mb-2" @click="moveWrite()" icon>
                <v-icon v-if="ok" large>{{ icons.mdiPencil }}</v-icon>
              </v-btn>
            </v-toolbar>
          </template>
        </v-data-table>
        <div>
          <br />
          <!-- <b-pagination-nav
            align="center"
            v-model="currentPage"
            :link-gen="linkGen"
            :number-of-pages="pageCount"
            use-router
          ></b-pagination-nav> -->
          <v-pagination v-model="page" :length="pageCount" :link-gen="linkGen"></v-pagination>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { listArticle } from "@/api/notice.js";
// import { getNews } from "@/api/searchdeal.js";
import { mapState } from "vuex";
// var parseString = require("xml2js").parseString;
import { mdiPencil } from "@mdi/js";

const memberStore = "memberStore";

export default {
  name: "NoticeList",
  components: {},
  computed: {
    ...mapState(memberStore, ["isLogin", "memberInfo"]),
  },
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      headers: [
        {
          text: "글번호",
          value: "noticeId",
          align: "center",
          sortable: false,
          width: "10%",
        },
        { text: "제목", value: "title", align: "center", sortable: false, width: "50%" },
        { text: "작성자", value: "author", sortable: false, width: "15%" },
        { text: "작성일", value: "date", sortable: false, width: "15%" },
      ],
      search: "",
      icons: {
        mdiPencil,
      },
      currentPage: 1,
      ok: false,
      news: null,
      param: {
        pg: this.currentPage,
        spp: 10,
        key: null,
        word: null,
      },
      articles: [],
    };
  },
  created() {
    let param = {
      pg: 1,
      spp: 10,
      key: null,
      word: null,
    };
    listArticle(
      param,
      (response) => {
        console.log(response.data);
        this.articles = response.data;
        this.pageCount = parseInt(this.articles.length / 10 + 1);
        if (this.memberInfo.memberId == "admin") {
          this.ok = true;
        } else {
          this.ok = false;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    alink(index) {
      console.log(this.news[index].newUrl[0]);
      location.href = "http://" + "" + this.news[index].newUrl[0];
    },
    linkGen() {
      console.log(this.currentPage);
      // this.param.pg = pageNum;
      // this.param.start = this.param.spp + 1;
      let param = {
        pg: this.currentPage,
        spp: 10,
        key: null,
        word: null,
      };
      listArticle(
        param,
        (response) => {
          this.articles = response.data;
        },
        (error) => {
          console.log(error);
        }
      );
      // return pageNum === 1 ? "?" : `?page=${pageNum}`;
    },
    moveWrite() {
      this.$router.push({ name: "NoticeWrite" });
    },
    viewArticle(item) {
      this.$router.push({
        name: "NoticeDetail",
        params: { noticeId: item.noticeId },
      });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
