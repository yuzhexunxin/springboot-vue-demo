<template>
  <div>
    <router-view/>
  </div>
</template>

<style>

</style>

<script>
  import Layout from "@/layout/Layout.vue";
  import router from "@/router";

  export default {
    name: "App",
    components: {
      Layout,
    },
  }

</script>