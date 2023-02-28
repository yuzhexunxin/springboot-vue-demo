<template>
  <div>
    <!--    头部-->
    <Header style="position: fixed;/*固定*/
            top: 0px;
            width: 100%;"/>
    <!--    左侧边栏-->
    <div style="margin-top: 50px;">
      <Aside style="position: fixed;/*固定*/
      left: 0px;"/>
      <!--    主体-->
      <div style="margin-left: 200px">
        <router-view />
      </div>

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