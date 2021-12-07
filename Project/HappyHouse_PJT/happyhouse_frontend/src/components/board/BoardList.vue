<template>
  <v-container>
    <p class="lead">자유롭게 게시글을 작성하고 토론 할 수 있습니다.</p>

    <v-card height="800">
      <v-tabs left>
        <v-tabs-slider></v-tabs-slider>
        <v-tab>게시판</v-tab>
        <v-tab>오늘의 뉴스</v-tab>

        <v-tab-item>
          <v-row align="center">
            <v-col align="left">
              <small class="font-weight-bold ml-4">현 페이지 총 게시물 {{ articles.length }}건</small>
            </v-col>
            <v-spacer></v-spacer>
            <v-col align="right">
              <v-btn color="primary" dark class="mr-4" @click="moveWrite()" icon>
                <v-icon large>{{ icons.mdiPencil }}</v-icon>
              </v-btn>
            </v-col>
          </v-row>
          <v-row>
            <v-col style="height: 100%">
              <v-data-table
                :headers="headers"
                :items="articles"
                :page.sync="page"
                :items-per-page="itemsPerPage"
                hide-default-footer
                class="elevation-1"
                @click:row="viewArticle"
              >
              </v-data-table>
              <div>
                <br />
                <b-pagination-nav
                  align="center"
                  v-model="currentPage"
                  :link-gen="linkGen"
                  :number-of-pages="pageCount"
                  use-router
                ></b-pagination-nav>
                <!-- <v-pagination v-model="page" :length="pageCount" :link-gen="linkGen"></v-pagination> -->
              </div>
            </v-col>
          </v-row>
        </v-tab-item>

        <v-tab-item>
          <div class="col-lg-12 border">
            <input type="text" v-model="search" /><button @click="searching">검색</button>
            <p class="lead text-black text-left" v-for="(anew, index) in news" :key="index">
              <strong>{{ anew.newsOriginNm[0] }}</strong>
              <br />
              <strong style="font-size: 15px" @click="alink(index)">
                {{ anew.newsSj[0] }}
              </strong>
            </p>
          </div>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-container>
</template>

<script>
import { listArticle } from "@/api/board.js";
import { getNews } from "@/api/searchdeal.js";
import { mdiPencil } from "@mdi/js";
var parseString = require("xml2js").parseString;

export default {
  name: "BoardList",
  components: {},
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      headers: [
        {
          text: "글번호",
          value: "boardId",
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
      news: null,
      currentPage: 1,
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
        this.articles = response.data;
        this.pageCount = this.articles.length / 10 + 1;
      },
      (error) => {
        console.log(error);
      }
    );
    getNews(
      {
        serviceKey: "cRv3SXAMhqr8WN3dHxlWGzd1Vac5Vc7uuEmPKZxOJeynn+yZqCoWq0woJvAGN0OGjTktx+PR8GWnhrAkoA+AOQ==",
        numOfRows: 10,
        pageNo: 1,
        searchSj: "팬데믹",
        searchOriginNm: "연합뉴스",
      },
      (response) => {
        // console.log(response.data);
        var self = this;
        parseString(response.data, function (err, result) {
          self.events = result;
          console.log(err);
        });
        this.news = self.events.WORLDJOB.BODY[0].ITEM;
        console.log(this.news);
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
    searching() {
      getNews(
        {
          serviceKey: "cRv3SXAMhqr8WN3dHxlWGzd1Vac5Vc7uuEmPKZxOJeynn+yZqCoWq0woJvAGN0OGjTktx+PR8GWnhrAkoA+AOQ==",
          numOfRows: 10,
          pageNo: 1,
          searchSj: this.search,
          searchOriginNm: "연합뉴스",
        },
        (response) => {
          var self = this;
          parseString(response.data, function (err, result) {
            self.events = result;
            console.log(result);
            console.log(err);
          });
          this.news = self.events.WORLDJOB.BODY[0].ITEM;
          console.log(this.news);
        },
        (error) => {
          console.log(error);
        }
      );
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
      this.$router.push({ name: "BoardWrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "BoardDetail",
        params: { boardId: article.boardId },
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
