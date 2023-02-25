<template>
  <div style="height: 50px;  line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue"><el-icon style="margin-right: 10px"><HomeFilled /></el-icon>后台管理系统</div>
    <div style="flex: 1;"></div>
    <div style="width: 100px; font-weight: bold; padding: 18px 0">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{nickname}}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click.native="$router.push('/self')"><el-icon><User /></el-icon>个人信息</el-dropdown-item>

            <el-dropdown-item @click.native="$router.push('/login')"><el-icon><SwitchButton /></el-icon>退出系统</el-dropdown-item>

          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {ArrowDown, HomeFilled, SwitchButton, User} from '@element-plus/icons'
export default {
  name: "Header",
  data(){
    return{
      nickname: '',
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.nickname = JSON.parse(userStr).nickname
  },
  components: {
    HomeFilled,
    User,
    SwitchButton,
    ArrowDown,
  }
}
</script>

<style scoped>
.el-dropdown .el-dropdown-link {
   cursor: pointer;
   color: var(--el-color-primary);
   display: flex;
   align-items: center;
 }
</style>