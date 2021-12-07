<template>
  <b-tr>
    <b-td>{{ boardId }}</b-td>
    <b-th class="text-left">
      <router-link :to="{ name: 'BoardDetail', params: { boardid: boardId } }">{{ title }}</router-link>
    </b-th>
    <b-td>-</b-td>
    <b-td>{{ author }}</b-td>
    <b-td>{{ date }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";

export default {
  name: "BoardListRow",
  props: {
    boardId: Number,
    author: String,
    title: String,
    // hit: Number,
    date: String,
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
};
</script>

<style></style>
