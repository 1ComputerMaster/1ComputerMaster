<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Notice",
};
</script>
<style scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(231, 27, 139, 0.3) 30%);
}
</style>
