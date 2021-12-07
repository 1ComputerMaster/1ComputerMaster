<template>
  <v-container>
    <notice-write-form type="modify" />
  </v-container>
</template>

<script>
import NoticeWriteForm from "./child/NoticeWriteForm.vue";

export default {
  name: "NoticeModify",
  components: {
    NoticeWriteForm,
  },
};
</script>

<style></style>
