<template>
  <div style="width: calc(100vw - 100px);padding: 10px">

    <el-descriptions
        class="margin-top"
        title="With border"
        :column="3"
        :size="size"
        border
        :model="form"
        size="large"
    >
      <el-descriptions-item size="large">
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user />
            </el-icon>
            用户名
          </div>
        </template>
        {{ form.userName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <location />
            </el-icon>
            昵称
          </div>
        </template>
        {{ form.nickname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <location />
            </el-icon>
            年龄
          </div>
        </template>
        {{ form.age }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <tickets />
            </el-icon>
            性别
          </div>
        </template>
<!--        <el-tag size="small">School</el-tag>-->
        {{ form.sex }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <iphone />
            </el-icon>
            电话
          </div>
        </template>
        18100000000
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <iphone />
            </el-icon>
            邮箱
          </div>
        </template>
        18100000000
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <office-building />
            </el-icon>
            地址
          </div>
        </template>
        {{ form.address }}
      </el-descriptions-item>
    </el-descriptions>
    <el-button type="primary" @click="handleEdit" >编辑</el-button>
    <el-dialog style="width: 40%;" v-model="dialogVisible">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.userName" style="width: 80%" disabled/>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.sex" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="form.address" style="width: 80%"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" style="width: 80%" show-password/>
        </el-form-item>
      </el-form>
      <div style="text-align: center" >
        <el-button type="primay" @click="update">
          保存
        </el-button>
      </div>
    </el-dialog>
  </div>
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
      dialogVisible: false,
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    console.log(this.form)
  },
  methods: {
    handleEdit(row) {
      this.dialogVisible = true
    },
    update(){
      request.put("/user",this.form).then(res => {
        console.log(res)
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
        this.dialogVisible= false
      })
    }
  }
}
</script>

<style scoped>

</style>