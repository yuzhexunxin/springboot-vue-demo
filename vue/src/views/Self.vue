<template>

  <div style="width: calc(100vw - 200px);height: 200px; padding: 30px; display: flex" >
    <div style="width: 200px;height: 200px;">
      <el-upload on-exceed="x" ref="upload" :show-file-list="false" :action="filesUploadUrl" :on-success="filesUploadSuccess">
        <img class="selfImg" :src="form.headimg?form.headimg:require('../assets/img/头像.jpg')"/>
      </el-upload>
    </div>
      <el-descriptions class="selfCard" title="个人信息" size="large" border="true">
        <el-descriptions-item label="用户名" width="100px"  style="background-color: red">
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{ form.userName }}
            </el-tooltip>
          </span>
          <el-input v-if="!inShow" v-model="form.userName"  style="margin: 0 auto; width: 150px;"/>
        </el-descriptions-item>
        <el-descriptions-item label="昵称" width="100px" >
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{ form.nickname }}
            </el-tooltip>
          </span>
          <el-input v-if="!inShow" v-model="form.nickname"  style="margin: 0 auto; width: 150px;"/>
        </el-descriptions-item>
        <el-descriptions-item label="年龄" width="100px">
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{ form.age }}
            </el-tooltip>
          </span>
          <el-input v-if="!inShow" v-model="form.age"  style="margin: 0 auto; width: 150px;"/>
        </el-descriptions-item>
        <el-descriptions-item label="性别" width="100px">
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{form.sex}}
            </el-tooltip>
          </span>
          <span v-if="!inShow" >
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
            <el-radio v-model="form.sex" label="未知">未知</el-radio>
          </span>
        </el-descriptions-item>
        <el-descriptions-item label="地址" width="100px">
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{ form.address }}
            </el-tooltip>
          </span>
          <el-input v-if="!inShow" v-model="form.address"  style="margin: 0 auto; width: 150px;"/>
        </el-descriptions-item>
        <el-descriptions-item label="密码" width="100px">
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{ form.password }}
            </el-tooltip>
          </span>
          <el-input v-if="!inShow" v-model="form.password"  style="margin: 0 auto; width: 150px;"/>
        </el-descriptions-item>
        <el-descriptions-item label="手机号" width="100px">
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{ form.phone }}
            </el-tooltip>
          </span>
          <el-input v-if="!inShow" v-model="form.phone"  style="margin: 0 auto; width: 150px;" />
        </el-descriptions-item>
        <el-descriptions-item label="邮箱" width="100px">
          <span v-if="inShow" @dblclick="edit" onselectstart="return false">
            <el-tooltip content="双击修改" placement="top-start">
              {{ form.email }}
            </el-tooltip>
          </span>
          <el-input v-if="!inShow" v-model="form.email"  style="margin: 0 auto; width: 150px;" />
        </el-descriptions-item>
      </el-descriptions>
  </div>
  <div v-if="!inShow" style="margin-top: 30px;float:right; margin-right: 30px">
    <el-button type="primary" @click="update" >保存</el-button>
  </div>

<!--  -->


</template>

<script>
import request from "@/utils/request";
import {User} from "@element-plus/icons";

export default {
  name: "Self",
  components: {User},
  data(){
    return{
      form: {},
      inShow: true,
      inValue: '',
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload"
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
  },
  methods: {
    x(){
      console.log("文件太大")
    },
    filesUploadSuccess(res){
      this.form.headimg = res.data
      this.update()
    },
    edit() {
      this.inShow = false
    },
    update(){
      request.put("/user",this.form).then(res => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user",JSON.stringify(this.form))
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      this.inShow = true
    }
  }
}
</script>

<style scoped>
.selfImg{
  width: 200px;
  height: 200px;
  border-radius: 50%;
  object-fit: cover;
}
.selfCard{
  width: 100%;;
  margin-bottom: 20px;
  margin-left: 50px;
}

</style>