<template>
  <v-tab-item elevate-on-scroll scroll-target="#scrolling-techniques-7">
    <v-sheet id="scrolling-techniques-7" class="overflow-y-auto" max-height="600">
      <v-container>
        <v-list three-line align="left">
          <v-list-item-group>
            <v-list-item v-for="(apt, index) in houses" :key="apt.aptName" @click="selectHouse(index)">
              <v-list-item-avatar>
                <v-icon>mdi-home</v-icon>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title v-text="apt.aptName"></v-list-item-title>
                <v-list-item-subtitle class="text--primary" v-text="apt.recentPrice"></v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-action>
                <v-btn v-if="apt.isHovered" @click="deleteMyLike(index)" icon>
                  <v-icon color="yellow darken-3"> mdi-bookmark </v-icon>
                </v-btn>
                <v-btn v-else @click="plus(index)" icon>
                  <v-icon color="grey lighten-1"> mdi-bookmark-outline </v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-container>
    </v-sheet>
  </v-tab-item>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";

const memberStore = "memberStore";
const houseStore = "houseStore";

export default {
  data() {
    return {
      selected: [],
    };
  },
  props: { houses: [] },
  computed: {
    ...mapState(memberStore, ["memberInfo"]),
  },
  mounted() {
    this.SET_HOUSES_HOVER();
    this.SET_MYLIKES_HOVER();
  },
  methods: {
    ...mapMutations(houseStore, ["SET_DONG_NAME", "SET_MOVE_HOUSE", "SET_HOUSES_HOVER", "SET_MYLIKES_HOVER"]),
    ...mapActions(houseStore, ["setLikeDeal", "deleteLikeDeal"]),
    selectHouse(index) {
      this.SET_MOVE_HOUSE(this.houses[index]);
      this.SET_DONG_NAME(this.houses[index].dongName);
    },
    async plus(index) {
      // my like plus function
      await this.setLikeDeal({
        memberId: this.memberInfo.memberId,
        aptCode: this.houses[index].aptCode,
      });
    },
    deleteMyLike(index) {
      // my like delete function
      console.log(this.houses[index].aptCode);
      this.deleteLikeDeal({
        aptCode: this.houses[index].aptCode,
        memberId: this.memberInfo.memberId,
      });
    },
  },
};
</script>
