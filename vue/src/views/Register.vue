<template>
  <div style="width: 100%; height: 100vh; background-color: black; overflow: hidden">
    <div style="width: 400px; margin: 120px auto">
      <div style="color: #cccccc; font-size: 40px; text-align: center; padding:30px 0">欢迎注册</div>
      <el-form :model="form" ref="from" size="large" :rules="rules">
        <el-form-item prop="userName">
          <el-input :prefix-icon="User" v-model="form.userName" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" v-model="form.password" show-password />
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input :prefix-icon="Lock" v-model="form.confirm" show-password />
        </el-form-item>
        <el-form-item >
          <el-button style="width: 100%;" type="primary" @click="register">注 册</el-button>
        </el-form-item>

      </el-form>
    </div>

  </div>
</template>

<script>
import {Lock, User} from "@element-plus/icons";
import request from "@/utils/request";

export default {
  name: "Register",
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
        confirm: [
          {required: true, message: '请确认密码', trigger: 'blur'},
        ],
      }
    }
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
    register(){
      if(this.form.password !== this.form.confirm){
        this.$message({
          type: "error",
          message: "两次密码不一致"
        })
        return
      }
      this.$refs['from'].validate((valid) => {
        console.log("s",this.form)
        if(valid) {
          this.form.role = 2
          request.post("/user/register", this.form).then(res => {
            if(res.code === "0") {
              this.$message({
                type:"success",
                message:"注册成功"
              })
              this.$router.push("/login")//登陆成功进行跳转主页
            } else{
              this.$message({
                type:"error",
                message:res.msg
              })
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