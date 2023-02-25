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
    created() {
      // 取出缓存的用户信息
      let userJson = sessionStorage.getItem("user")
      if(!userJson){
        router.push("/login")
      }
    }
  }

</script>