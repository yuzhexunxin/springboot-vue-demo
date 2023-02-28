<template>
  <div style="width: 100%; height: 100vh; background-color: black; overflow: hidden">
    <div style="width: 400px; margin: 120px auto">
      <div style="color: #cccccc; font-size: 40px; text-align: center; padding:30px 0">欢迎登录</div>
      <el-form :model="form" ref="from" size="large" :rules="rules">
        <el-form-item  prop="userName">
          <el-input :prefix-icon="User" v-model="form.userName" />
        </el-form-item>
        <el-form-item  prop="password">
          <el-input :prefix-icon="Lock" v-model="form.password" @keyup.enter="login" show-password />
        </el-form-item>
        <el-form-item >
          <el-button style="width: 100%;" type="primary" @click="login">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
import {Lock, User} from "@element-plus/icons";
import request from "@/utils/request";

export default {
  name: "Login",
  data(){
    return{
      form: {},
      rules: {
        userName: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    }
  },
  created(){
    sessionStorage.removeItem("user")
  },
  computed: {
    User() {
      return User
    },
    Lock() {
      return Lock
    },
  },
  methods: {
    login(){
      this.$refs['from'].validate((valid) => {
        if(valid) {
          request.post("/user/login", this.form).then(res => {
            if (res.code === "0") {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user",JSON.stringify(res.data))
              this.$router.push("/")//登陆成功进行跳转主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
              console.log("失败")
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>