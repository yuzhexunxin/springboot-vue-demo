<template>
  <div style="width: calc(100vw - 200px); padding: 30px; overflow: hidden;">
    <div style="display: flex; margin-bottom: 25px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
      <!--      <el-input v-model="search" @keyup.enter="load" placeholder="请输入关键字搜索" style="width: 60%; margin-left: 173px" clearable/>-->
      <!--      <el-button type="primary" style="margin-left: 10px" @click="load">搜索</el-button>-->
      <!--      搜索框-->
      <el-input
          v-model="search"
          @keyup.enter="load"
          placeholder="请输入关键字检索"
          style="width: 60%; margin-left: 173px"
          clearable
      >
        <!--      分类-->
        <template #prepend>
          <el-select effect="light" v-model="keyWord" placeholder="分类" style="width: 75px;">
            <el-option label="名称" value="title" />
            <el-option label="楼层" value="floor" />
          </el-select>
        </template>
        <!--      按钮-->
        <template #append>
          <el-button :icon="Search" type="primary" @click="load" ></el-button>
        </template>
      </el-input>
    </div>
    <!--    主体区域-->
    <el-table :data="tableData" stripe border style="width: 100%">
      <el-table-column prop="id" label="ID" align="center" sortable />
      <el-table-column prop="title" label="展览名称" align="center" />
      <el-table-column prop="floor" label="楼层" align="center" style="height: 600px"/>
      <el-table-column prop="time" label="展览时间" align="center" />
<!--      <el-table-column prop="imglist" label="展品年份" align="center" style="height: 600px"/>-->
      <el-table-column label="展览图片" style="background-color: aqua" align="center" >
        <template #default="scope" >
          <el-image
              style="width: 100px; height: 100px; "
              :src="scope.row.headimg"
              :preview-src-list="[scope.row.headimg]"
              preview-teleported="true"
          />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="200px" >
        <!--        删除确认提示框-->
        <template #default="scope">
          <el-button type="primary" @click="details(scope.row)" >详情</el-button>
          <el-button type="primary" @click="handleEdit(scope.row)" >编辑</el-button>
          <el-popconfirm
              width="220"
              confirm-button-text="是的"
              cancel-button-text="不，谢谢"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="确定删除吗?"
              @confirm="handleDelete(scope.row.id)"
          >

            <template #reference>
              <el-button type="danger" color="red">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 10px">
      <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[4, 8, 16]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
      <!--      弹窗-->
      <el-dialog v-model="dialogVisible" title="信息" width="50%" style="margin-top: 10vh">
        <el-form :model="form" label-width="120px">
          <el-form-item label="展览名称">
            <el-input v-model="form.title" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="展览楼层">
            <el-input v-model="form.floor" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="展览时间">
            <el-date-picker v-model="form.time" value-format="YYYY-MM-DD HH:mm:ss" type="date" style="width: 80%" clearable></el-date-picker>
          </el-form-item>
          <el-form-item label="展览图片">
            <el-upload ref="upload" :action="filesUploadUrl" :on-success="filesUploadSuccess"
            >
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <div id="div1"></div>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>
      <!--      详情弹窗-->
      <el-dialog v-model="vis" :title="detail.title" width="50%">
        <div style="margin-top: -20px;margin-bottom: 20px">展览时间：{{ detail.time }}</div>
        <el-card>
          <div v-html="detail.content" style="min-height: 100px"></div>
        </el-card>
      </el-dialog>
    </div>
  </div>


</template>

<script>
// @ is an alias to /src
import {Search ,InfoFilled} from "@element-plus/icons";
import request from "@/utils/request";
import E from "wangeditor";
let editor
export default {
  name: 'ExhAdmin',
  data() {
    return {
      form: {},
      dialogVisible: false,
      search: '',
      pageNum: 1,
      pageSize: 4,
      total: 0,
      tableData: [],
      keyWord: 'title',
      detail: {},
      vis: false,
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload"
    }
  },
  computed: {
    InfoFilled(){
      return InfoFilled
    },
    Search(){
      return Search
    }
  },
  created() {
    this.load()
  },
  methods: {
    details(row) {
      this.detail = row
      this.vis = true
    },
    filesUploadSuccess(res){
      console.log(res)
      this.form.headimg = res.data
    },
    load(){
      request.get("/zl",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          keyWord:this.keyWord,
          search:this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add() {
      if(editor) {
        editor.destroy()
      }
      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() => {
        editor = new E("#div1")
        // editor.config.uploadImgServer = '/api/files/editor/upload'
        editor.config.uploadImgServer = 'http://' + window.server.filesUploadUrl + ':9090/files/editor/upload'
        editor.config.uploadFileName = "file"
        editor.create()
        this.$refs['upload'].clearFiles() //清楚历史文件列表
      })
    },
    save() {
      this.form.content = editor.txt.html() //获取编辑器值，放入对象属性
      if(this.form.id){//更新
        request.put("/zl",this.form).then(res => {
          console.log(res)
          if(res.code === "0") {
            this.$message({
              type:"success",
              message:"更新成功"
            })
          } else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()//刷新数据
          this.dialogVisible = false
        })
      } else{
        request.post("/zl",this.form).then(res => {
          console.log(res)
          if(res.code === "0") {
            this.$message({
              type:"success",
              message:"新增成功"
            })
          } else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()//刷新数据
          this.dialogVisible = false
        })
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      if(editor) {
        editor.destroy()
      }
      this.$nextTick(() => {
        editor = new E("#div1")
        editor.config.uploadImgServer = '/api/files/editor/upload'
        editor.config.uploadFileName = "file"
        editor.create()
        editor.txt.html(row.content)
        this.$refs['upload'].clearFiles() //清楚历史文件列表
      })
    },
    handleDelete(id){
      request.delete("/zl/" + id).then(res =>{
        if(res.code === "0") {
          this.$message({
            type:"success",
            message:"删除成功"
          })
        } else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()//刷新数据
      })
    },
    handleSizeChange() {//改变条数触发
      this.load()
    },
    handleCurrentChange() {//改变页码触发
      this.load()
    }
  },
}
</script>
<style scoped>

</style>
