import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import Ads from "vue-google-adsense";

Vue.use(require("vue-script2"));
Vue.use(Ads.Adsense); //디스플레이 광고
// Vue.use(Ads.InArticleAdsense); //콘텐츠 내 자동 삽입 광고
// Vue.use(Ads.InFeedAdsense); //인피드

// Import Bootstrap an BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import store from "./store";
// import memberStore from "./store/modules/memberStore";
import vuetify from "./plugins/vuetify";

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);

Vue.config.productionTip = false;

new Vue({
  router,
  store,

  // async beforeCreate() {
  //   let token = sessionStorage.getItem("access-token");
  //   if (memberStore.state.memberInfo == null && token) {
  //     await memberStore.dispatch("getMemberInfo", token);
  //   }
  // },

  vuetify,
  render: (h) => h(App),
}).$mount("#app");
