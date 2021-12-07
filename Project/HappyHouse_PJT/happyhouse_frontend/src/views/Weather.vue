<template>
  <div>
    <h3 class="underline-orange"><b-icon icon="house-fill"></b-icon> House Service</h3>
    <b-row>
      <b-col>
        <house-search-bar></house-search-bar>
      </b-col>
    </b-row>
    기온 : {{ weather.item[3].obsrValue[0] }} ℃ | 습도 : {{ weather.item[1].obsrValue[0] }} % | 1시간 강수량 :
    {{ weather.item[2].obsrValue[0] }} mm | 동서 바람 성분 : {{ weather.item[4].obsrValue[0] }} m/s | 풍향 :
    {{ weather.item[5].obsrValue[0] }} deg | 남북 바람 성분 : {{ weather.item[6].obsrValue[0] }} m/s | 풍속
    {{ weather.item[7].obsrValue[0] }} m/s
    <button @click="search">테스트</button>
    <b-row>
      <b-col cols="6" align="left">
        <house-list />
      </b-col>
      <b-col cols="6">
        <house-detail />
      </b-col>
    </b-row>
  </div>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import { getWeather } from "@/api/searchdeal.js";
var parseString = require("xml2js").parseString;
const func = require("@/config/convertXY");
let today = new Date();

let year = today.getFullYear(); // 년도
let month = today.getMonth() + 1; // 월
let date = today.getDate(); // 날짜

let hours = today.getHours(); // 시
if (hours < 10) {
  hours = "0" + hours;
}
let minutes = today.getMinutes(); // 분

//실제 바로 쓰는 것들
let str = year + "" + month + "" + date; //이건 바뀌지 않음 키에 넣으면 됨
let key = "cRv3SXAMhqr8WN3dHxlWGzd1Vac5Vc7uuEmPKZxOJeynn+yZqCoWq0woJvAGN0OGjTktx+PR8GWnhrAkoA+AOQ=="; //서비스 키

export default {
  name: "House",
  data() {
    return { weather: null };
  },
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
  },
  methods: {
    search() {
      let rs = func.toXY(33.450701, 126.570667);
      getWeather(
        {
          serviceKey: key,
          numOfRows: 10,
          pageNo: 1,
          base_date: str,
          base_time: hours + "" + minutes,
          nx: rs.x,
          ny: rs.y,
        },
        (response) => {
          var self = this;
          parseString(response.data, function (err, result) {
            self.events = result;
            // console.log(result);
            // console.log(err);
          });
          // console.log(self.events.response.body[0].items[0]);
          this.weather = self.events.response.body[0].items[0];
          // this.weather = self;
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
  created() {
    // let rs = func.toXY(33.450701, 126.570667);
    // getWeather(
    //   {
    //     serviceKey: key,
    //     numOfRows: 10,
    //     pageNo: 1,
    //     base_date: str,
    //     base_time: hours + "" + minutes,
    //     nx: rs.x,
    //     ny: rs.y,
    //   },
    //   (response) => {
    //     var self = this;
    //     parseString(response.data, function (err, result) {
    //       self.events = result;
    //       // console.log(result);
    //       // console.log(err);
    //     });
    //     // console.log(self.events.response.body[0].items[0]);
    //     this.weather = self.events.response.body[0].items[0];
    //     // this.weather = self;
    //   },
    //   (error) => {
    //     console.log(error);
    //   }
    // );
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(231, 149, 27, 0.3) 30%);
}
</style>
