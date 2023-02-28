<template>
  <div style="width: 100%; height: 100vh; background-color: black; overflow: hidden">
    <div style="width: 400px; margin: 120px auto">
      <div style="color: #cccccc; font-size: 40px; text-align: center; padding:30px 0">欢迎登录</div>
      <el-form :model="form" ref="from" size="large" :rules="rules">
        <el-form-item  prop="userName">
          <el-input :prefix-icon="User" v-model="form.userName" placeholder="请输入用户名" clearable/>
        </el-form-item>
        <el-form-item  prop="password">
          <el-input :prefix-icon="Lock" v-model="form.password" placeholder="请输入密码" show-password clearable/>
        </el-form-item>
        <div style="display: flex">
          <el-input :prefix-icon="Link" v-model="form.validCode" @keyup.enter="login" placeholder="请输入验证码" clearable/>
          <ValidateCode ref="ref_validCode" @change="changeCode" style="margin-left: 10px"></ValidateCode>
        </div>
        <el-form-item >
          <el-button style="width: 100%;margin-top: 20px" type="primary" @click="login">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
import {Lock, User, Link} from "@element-plus/icons";
import request from "@/utils/request";
import ValidateCode from "@/components/ValidateCode.vue";
export default {
  name: "Login",
  data(){
    return{
      user: {},
      validCode: '', //验证码
      form: {}, //表单
      rules: {  //用户密码验证规则
        userName: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    }
  },
  components: {
    ValidateCode
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
    Link() {
      return Link
    }
  },
  methods: {
    changeCode(value) {
     this.validCode = value
    },
    login(){
      this.$refs['from'].validate((valid) => {
        if(valid) {
          if(!this.form.validCode) {
            this.$message.error("请输入验证码")
            this.form.validCode = ""
            this.$refs["ref_validCode"].draw();
          }else if(this.form.validCode.toUpperCase() !== this.validCode) {
            this.$message.error("验证码错误")
            this.form.validCode = ""
            this.$refs["ref_validCode"].draw();
          }else {
            request.post("/user/login", this.form).then(res => {
              if (res.code === "0") {
                if(res.data.role === 1) {
                  this.$message({
                    type: "success",
                    message: "登录成功"
                  })
                  sessionStorage.setItem("user",JSON.stringify(res.data))
                  this.$router.push("/")//登陆成功进行跳转主页
                }else{
                  this.$message.error("普通用户无法登录")
                }
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          }
        }
      })

    }
  }
}
</script>

<style scoped>

</style>