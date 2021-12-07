<template>
  <div class="text-center">
    <v-menu v-model="menu" :close-on-content-click="false" :nudge-width="200" offset-x left>
      <template v-slot:activator="{ on, attrs }">
        <v-fab-transition>
          <v-btn color="indigo" absolute right bottom dark fab v-bind="attrs" v-on="on"><v-icon>mdi-cloud</v-icon></v-btn>
        </v-fab-transition>
      </template>

      <v-card class="mx-auto" max-width="400">
        <v-list-item two-line>
          <v-list-item-content>
            <v-list-item-title class="text-h5"> {{ dongName }} </v-list-item-title>
            <v-list-item-subtitle>{{ date }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-card-text>
          <v-row align="center">
            <v-col class="text-h2" cols="6"> {{ temperature }} </v-col>
            <v-col cols="6">
              <v-img src="https://cdn.vuetifyjs.com/images/cards/sun.png" alt="Sunny image" width="92"></v-img>
            </v-col>
          </v-row>
        </v-card-text>

        <v-list-item>
          <v-list-item-icon>
            <v-icon>mdi-send</v-icon>
          </v-list-item-icon>
          <v-list-item-subtitle>{{ windSpeed }} m/s</v-list-item-subtitle>
        </v-list-item>

        <v-list-item>
          <v-list-item-icon>
            <v-icon>mdi-cloud-download</v-icon>
          </v-list-item-icon>
          <v-list-item-subtitle>{{ humidity }}%</v-list-item-subtitle>
        </v-list-item>
      </v-card>
    </v-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      labels: ["SU", "MO", "TU", "WED", "TH", "FR", "SA"],
      time: 0,
      forecast: [
        { day: "Tuesday", icon: "mdi-white-balance-sunny", temp: "24\xB0/12\xB0" },
        { day: "Wednesday", icon: "mdi-white-balance-sunny", temp: "22\xB0/14\xB0" },
        { day: "Thursday", icon: "mdi-cloud", temp: "25\xB0/15\xB0" },
      ],
      date: "",
      // 습도, 기온, 풍속
      humidity: 0,
      temperature: 0,
      windSpeed: 0,
    };
  },
  created() {
    this.getWeather();
    this.getDate();
  },
  props: {
    weathers: [],
    dongName: null,
  },
  methods: {
    getDate() {
      let d = new Date();
      let h = d.getHours();
      let m = d.getMinutes();
      let day = d.getDay();

      let days = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
      this.date = `${days[day]} ${h} : ${m}`;
    },
    getWeather() {
      this.humidity = this.weathers[1].obsrValue;
      this.temperature = this.weathers[3].obsrValue;
      this.windSpeed = this.weathers[7].obsrValue;
    },
  },
};
</script>
