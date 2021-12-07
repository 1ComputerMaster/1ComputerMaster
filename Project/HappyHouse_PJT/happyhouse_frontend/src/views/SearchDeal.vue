<template>
  <div>
    <v-row style="height: 850px">
      <v-col cols="4">
        <v-card color="accent-1">
          <v-form>
            <v-container fluid>
              <v-row align="center">
                <v-col cols="12">
                  <v-text-field v-model="msg" type="text" append-outer-icon="mdi-send" clearable @click:append-outer="addInterBtn">
                  </v-text-field>
                </v-col>
              </v-row>
              <house-search-bar></house-search-bar>
              <v-row>
                <v-col>
                  <v-sheet>
                    <v-chip v-for="(apt, index) in inters" :key="index" close @click:close="remove(index)" class="ma-2">
                      {{ apt }}
                    </v-chip>
                  </v-sheet>
                  <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
                    <v-tab>거래 검색</v-tab>
                    <v-tab>관심 거래</v-tab>
                    <!-- <v-tab>관심 지역</v-tab> -->
                  </v-tabs>
                  <v-tabs-items v-model="tab">
                    <house-search-list :houses="houses"></house-search-list>
                    <house-search-list :houses="mylikes"></house-search-list>
                  </v-tabs-items>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </v-card>
      </v-col>
      <v-col cols="8">
        <kakao-map ref="kakaomap" class="kakaomap"></kakao-map>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseSearchList from "@/components/house/HouseSearchList.vue";
import KakaoMap from "@/components/KakaoMap.vue";
import { mapState, mapMutations, mapActions } from "vuex";

const houseStore = "houseStore";
const memberStore = "memberStore";

export default {
  name: "SearchDeal",
  data() {
    return {
      weather: null,
      msg: "",
      likeId: "",
      tab: null,
      inters: [],
    };
  },
  components: {
    HouseSearchBar,
    HouseSearchList,
    KakaoMap,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "memberInfo"]),
    ...mapState(houseStore, ["houses", "mylikes", "myInter", "location", "dongs"]),
  },
  watch: {
    houses(newHouses) {
      if (this.isLogin) {
        for (const like of this.mylikes) {
          for (const key in newHouses) {
            if (newHouses[key].aptCode === like.aptCode) {
              newHouses[key].isMylike = true;
            } else {
              newHouses[key].isMylike = false;
            }
          }
        }
      }
    },
    myInter(newMyInter) {
      this.inters.length = 0;

      for (const key in newMyInter) {
        this.inters.push(newMyInter[key].dongName);
      }
    },
  },
  mounted() {
    this.getInterest({
      memberId: this.memberInfo.memberId,
    });
  },
  methods: {
    ...mapMutations(houseStore, ["SET_LOCATION"]),
    ...mapActions(houseStore, ["searchHouseList", "setLikeDeal", "deleteLikeDeal", "setInterest", "removeInterest", "getInterest"]),
    deleteDong(index) {
      this.removeInterest({
        dongCode: this.myInter[index].dongCode,
        memberId: this.memberInfo.memberId,
      });
    },
    high() {
      // HighId(
      //   ({ data }) => {
      //     console.log(data[0] + 1);
      //     this.likeId = data[0] + 1;
      //   },
      //   (error) => {
      //     console.log(error);
      //   }
      // );
    },
    where(index) {
      this.SET_LOCATION({
        lat: this.mylikes[index].lat,
        lng: this.mylikes[index].lng,
      });
    },
    addInterBtn() {
      console.log(this.msg);
      this.setInterest({
        dongName: this.msg,
        memberId: this.memberInfo.memberId,
      });
    },
    remove(index) {
      this.inters.splice(index, 1);
      this.removeInterest(this.myInter[index]);
    },
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(72, 190, 233, 0.3) 30%);
}
.kakaomap {
  height: 100%;
  width: 100%;
}
</style>
