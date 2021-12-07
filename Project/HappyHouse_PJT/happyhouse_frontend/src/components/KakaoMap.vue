<template>
  <div>
    <div id="map"></div>
    <weather :weathers="weathers" :dongName="dongName" />
  </div>
</template>

<script>
import { mapState } from "vuex";
import { mapActions } from "vuex";
import Weather from "@/components/Weather.vue";

const func = require("@/config/convertXY");

const houseStore = "houseStore";
const WeatherStore = "WeatherStore";

export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markers: [],
      overlays: [],
    };
  },
  created() {
    this.weather_test({
      lat: 33.450701,
      lng: 126.570667,
    });
  },
  components: {
    Weather,
  },
  props: {},
  computed: {
    ...mapState(houseStore, ["location", "dongName", "houses", "house"]),
    ...mapState(WeatherStore, ["weathers"]),
  },
  watch: {
    location(newLocation) {
      this.setCenter(newLocation);
      this.weather_test(newLocation);
    },
    houses(newHouses) {
      this.setMarkers(newHouses);
    },
    house(newHouse) {
      this.lookDetailHouse(newHouse);
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
      this.setCenter(this.location);
      this.setMarkers(this.houses);
    } else {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=9180176755ff9af97a0d34fe8e8ea0ad";
      document.head.appendChild(script);
      // this.setCenter();
      // this.setMarkers();
    }
  },
  methods: {
    ...mapActions(WeatherStore, ["getWeather"]),
    setCenter(location) {
      // 이동할 위도 경도 위치를 생성합니다
      let iwPosition = new kakao.maps.LatLng(location.lat, location.lng);
      // 지도 중심을 이동 시킵니다
      this.map.setCenter(iwPosition);
      // let marker = new kakao.maps.Marker({
      //   position: iwPosition,
      // });
      // marker.setMap(this.map);
    },
    makeOverListener(map, customOverlay) {
      return function () {
        customOverlay.setMap(map); // 마커 처음 클릭시 커스텀오버레이 맵에 띄우는애
      };
    },
    setMarkers(houses) {
      this.markers.forEach((element) => {
        element.setMap(null);
      });
      this.markers.length = 0;

      houses.forEach((element) => {
        // markers.push(
        this.markers.push(
          new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(element.lat, element.lng),
          })
        );
      });

      this.setOverlay(houses);
      for (const key in this.markers) {
        kakao.maps.event.addListener(this.markers[key], "click", this.makeOverListener(this.map, this.overlays[key]));

        // kakao.maps.event.addListener(this.markers[key], "click", () => {
        //   this.overlays[key].open(this.map, this.markers[key]);
        // });
      }
    },
    setOverlay(houses) {
      this.overlays.length = 0;
      houses.forEach((element) => {
        console.log(element);
        this.overlays.push(
          new kakao.maps.InfoWindow({
            position: new kakao.maps.LatLng(element.lat, element.lng),
            removable: true,
            content: `<div class="overlaybox rounded bg-dark">
        <div class="text-left text-light">${element.aptName}</div>
        <div class="first">
            <div class="movietitle text">${element.recentPrice}₩</div>
        </div>
        <ul>
            <li class= text-center">
                <span class=""><small>${element.sidoName} ${element.gugunName} ${element.dongName}<br/> ${element.jibun}</small></span>
            </li>
            <li>
                <p class="title"><small>최근 거래 : ${element.buildYear}</small></p>
            </li>
       </ul>
    </div>`,
          })
        );
      });
    },
    lookDetailHouse(house) {
      this.markers.forEach((element) => {
        element.setMap(null);
      });
      this.markers.length = 0;

      for (const overlay of this.overlays) {
        overlay.setMap(null);
      }
      this.overlays.length = 0;

      this.setCenter(house);
      this.markers.push(
        new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(house.lat, house.lng),
        })
      );
      this.overlays.push(
        new kakao.maps.InfoWindow({
          position: new kakao.maps.LatLng(house.lat, house.lng),
          removable: true,
          map: this.map,
          content: `<div class="overlaybox rounded bg-dark">
        <div class="text-left text-light">${house.aptName}</div>
        <div class="first">
            <div class="movietitle text">${house.recentPrice}₩</div>
        </div>
        <ul>
            <li class= text-center">
                <span class=""><small>${house.sidoName} ${house.gugunName} ${house.dongName}<br/> ${house.jibun}</small></span>
            </li>
            <li>
                <p class="title"><small>최근 거래 : ${house.buildYear}</small></p>
            </li>
       </ul>
    </div>`,
        })
      );
      kakao.maps.event.addListener(this.markers[0], "click", () => {
        this.overlays[0].open(this.map, this.markers[0]);
      });
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);
    },
    weather_test(location) {
      let rs = func.toXY(location.lat, location.lng);

      this.getWeather({
        nx: rs.x,
        ny: rs.y,
      });
    },
  },
};
</script>

<style scope>
#map {
  height: 100%;
  width: 100%;
}
.infowindow {
  height: 50px;
  width: 100px;
}
.overlaybox {
  position: relative;
  width: 280px;
  height: 350px;
  /* background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/box_movie.png") no-repeat; */
  padding: 15px 30px;
  opacity: 0.95;
}
.overlaybox div,
ul {
  overflow: hidden;
  margin: 0;
  padding: 0;
}
.overlaybox li {
  list-style: none;
}
.overlaybox .boxtitle {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png") no-repeat right;
  margin-bottom: 8px;
}
.overlaybox .first {
  position: relative;
  width: 210px;
  height: 136px;
  background: url("https://pix10.agoda.net/hotelImages/66178/0/a6814d8a47dd5b7870c568bd159033d1.jpg?s=400x136") no-repeat;
  margin-bottom: 8px;
}
.first .text {
  color: #fff;
  font-weight: bold;
}
.first .triangle {
  position: absolute;
  width: 48px;
  height: 48px;
  top: 0;
  left: 0;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/triangle.png") no-repeat;
  padding: 6px;
  font-size: 18px;
}
.first .movietitle {
  position: absolute;
  width: 100%;
  bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  padding: 7px 15px;
  font-size: 14px;
}
.overlaybox ul {
  width: 225px;
}
.overlaybox li {
  width: 90%;
  position: relative;
  margin-bottom: 4px;
  background: #2b2d36;
  padding: 4px 5px;
  color: #aaabaf;
  line-height: 1;
}
.overlaybox li span {
  display: inline-block;
}
.overlaybox li .number {
  font-size: 16px;
  font-weight: bold;
}
.overlaybox li .title {
  font-size: 13px;
}
.overlaybox ul .arrow {
  position: absolute;
  margin-top: 8px;
  right: 25px;
  width: 5px;
  height: 3px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/updown.png") no-repeat;
}
.overlaybox li .up {
  background-position: 0 -40px;
}
.overlaybox li .down {
  background-position: 0 -60px;
}
.overlaybox li .count {
  position: absolute;
  margin-top: 5px;
  right: 15px;
  font-size: 10px;
}
.overlaybox li:hover {
  color: #fff;
  background: #d24545;
}
.overlaybox li:hover .up {
  background-position: 0 0px;
}
.overlaybox li:hover .down {
  background-position: 0 -20px;
}
</style>
