<template>
  <b-tr>
    <b-td>{{ noticeId }}</b-td>
    <b-th class="text-left">
      <router-link :to="{ name: 'NoticeDetail', params: { noticeid: noticeId } }">{{ title }}</router-link>
    </b-th>
    <!-- <b-td>{{ hit }}</b-td> -->
    <b-td>{{ memberId }}</b-td>
    <b-td>{{ date }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";

export default {
  name: "NoticeListRow",
  props: {
    noticeId: Number,
    author: String,
    title: String,
    // hit: Number,
    date: String,
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.date)).format("YY.MM.DD hh:mm:ss");
    // },
  },
};
</script>

<style></style>
