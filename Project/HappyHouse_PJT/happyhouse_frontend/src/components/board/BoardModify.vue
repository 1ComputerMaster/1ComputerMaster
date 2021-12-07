<template>
  <v-container>
    <board-write-form type="modify" />
  </v-container>
</template>

<script>
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardModify",
  components: {
    BoardWriteForm,
  },
};
</script>

<style></style>
