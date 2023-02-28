<template>
  <div>
    <el-menu
        style="width: 200px; height: calc(100vh - 50px);"
        :default-active="path"
        router
        class="el-menu-vertical-demo">
      <!--        @open="handleOpen"-->
      <!--        @close="handleClose"-->
      <el-sub-menu index="museumAdmin">
        <template #title>
          <el-icon><location /></el-icon>
          <span>博物馆信息管理</span>
        </template>
        <el-menu-item index="/bscInfoAdmin">博物馆基本信息管理</el-menu-item>
        <el-menu-item index="/exhAdmin">博物馆展品信息管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/userAdmin" v-if="user.role === 1">
        <el-icon><User /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="/cmntAdmin" >
        <el-icon><document /></el-icon>
        <span>评论管理</span>
      </el-menu-item>
      <el-menu-item index="/self" >
        <el-icon><User /></el-icon>
        <span>个人信息</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {Setting, Document, Location, User} from '@element-plus/icons'
import request from "@/utils/request";


export default {
  name: "Aside",
  data(){
    return{
      path: this.$route.path,
      user: {}
    }
  },
  components: {
    User,
    Setting,
    Document,
    Location,
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  }

}
</script>

<style scoped>

</style>