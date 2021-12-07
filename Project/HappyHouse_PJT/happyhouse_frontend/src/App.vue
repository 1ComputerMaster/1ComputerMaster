<template>
  <v-app id="App">
    <navi-bar />
    <!-- <v-sheet id="scrolling-techniques-7" class="overflow-y-auto" height="100%"> -->
    <v-parallax dark src="https://cdn.vuetifyjs.com/images/backgrounds/vbanner.jpg">
      <v-row justify="center">
        <v-col class="text-center" cols="12" style="margin-top: 20px">
          <div class="display-4">HAPPY HOUSE</div>
          <h1 class="text-h4 font-weight-thin mb-4">{{ title }}</h1>
        </v-col>
      </v-row>
    </v-parallax>
    <v-card class="mx-auto" width="70%" height="100%" style="margin-top: -100px">
      <v-container fluid class="text-center" style="max-width: 100%">
        <router-view />
      </v-container>
    </v-card>
    <!-- </v-sheet> -->
    <!-- <v-container fluid class="bv-example-row mt-3 text-center" style="max-width: 80%">
          </v-container> -->
    <footer-bar />
  </v-app>
</template>

<script>
import NaviBar from "./components/layout/NaviBar.vue";
import FooterBar from "./components/layout/Footer.vue";

import { mapState } from "vuex";

const titleStore = "titleStore";

export default {
  name: "App",
  data() {
    return {
      appTitle: "",
    };
  },
  components: {
    NaviBar,
    FooterBar,
  },
  computed: {
    ...mapState(titleStore, ["title"]),
  },
};
</script>

<style>
a:hover {
  text-decoration: none;
  font-weight: bold;
}

a.router-link-exact-active {
  color: #42b983;
}
</style>
