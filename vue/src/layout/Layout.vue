<template>
  <div>
    <!--    头部-->
    <Header style="position: fixed;/*固定*/
            top: 0px;
            width: 100%;"/>
    <!--    左侧边栏-->
    <div style="display: flex;margin-top: 50px;">
      <Aside style="position: fixed;/*固定*/
      left: 0px;"/>
      <!--    主体-->
      <router-view style="margin-left: 200px"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Aside from "@/components/Aside.vue";
  export default {
    name: "Layout",
    components:{
      Header,
      Aside,
    }
  }
</script>

<style scoped>

</style>