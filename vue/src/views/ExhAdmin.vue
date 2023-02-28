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
            <el-option label="名称" value="exhName" />
            <el-option label="类型" value="exhType" />
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
      <el-table-column prop="exhId" label="ID" align="center" sortable />
      <el-table-column prop="exhName" label="展品名" align="center" />
      <el-table-column prop="exhType" label="展品类型" align="center" />
      <el-table-column prop="exhAge" label="展品年份" align="center" style="height: 600px"/>
<!--      <el-table-column prop="exhText" label="展品介绍" align="center" />-->
      <el-table-column label="展品图片" style="background-color: aqua" align="center" >
        <template #default="scope" >
          <el-image
              style="width: 100px; height: 100px; "
              :src="scope.row.exhImg"
              :preview-src-list="[scope.row.exhImg]"
              preview-teleported="true"
          />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="150px" >
        <!--        删除确认提示框-->
        <template #default="scope">
          <el-button type="primary" @click="handleEdit(scope.row)" >编辑</el-button>
          <el-popconfirm
              width="220"
              confirm-button-text="是的"
              cancel-button-text="不，谢谢"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="确定删除吗?"
              @confirm="handleDelete(scope.row.exhId)"
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
      <el-dialog v-model="dialogVisible" title="信息" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="展品名">
            <el-input v-model="form.exhName" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="展品类型">
            <el-input v-model="form.exhType" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="展品年份">
            <el-input v-model="form.exhAge" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="展品介绍">
            <el-input type="textarea" v-model="form.exhText" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="展品图片">
            <el-upload ref="upload" :action="filesUploadUrl" :on-success="filesUploadSuccess"
            >
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>

        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>


</template>

<script>
// @ is an alias to /src
import {Search ,InfoFilled} from "@element-plus/icons";
import request from "@/utils/request";

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
      keyWord: 'exhName',
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
    filesUploadSuccess(res){
      console.log(res)
      this.form.exhImg = res.data

    },
    load(){
      request.get("/exh",{
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
      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() => {
        this.$refs['upload'].clearFiles() //清楚历史文件列表
      })
    },
    save() {
      if(this.form.exhId){//更新
        request.put("/exh",this.form).then(res => {
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
        request.post("/exh",this.form).then(res => {
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
      this.$nextTick(() => {
        this.$refs['upload'].clearFiles() //清楚历史文件列表
      })
    },
    handleDelete(id){
      request.delete("/exh/" + id).then(res =>{
        if(res.code ==- "0") {
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