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
          <el-select effect="light" v-model="keyWord" placeholder="分类" style="width: 100px;">
            <el-option label="评论内容" value="text" />
            <el-option label="用户id" value="userid" />
            <el-option label="展品id" value="exhid" />
            <el-option label="展览id" value="zlid" />
            <el-option label="新闻id" value="newid" />
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
      <el-table-column prop="id" label="ID" align="center" sortable/>
      <el-table-column prop="text" label="评论内容" align="center" />
      <el-table-column prop="userid" label="评论用户id" align="center" />
      <el-table-column prop="exhid" label="评论展品id" align="center" />
      <el-table-column prop="zlid" label="评论展览id" align="center" />
      <el-table-column prop="newid" label="评论新闻id" align="center" />
      <el-table-column prop="date" label="评论时间" align="center" />
      <el-table-column prop="type" label="评论类型" align="center" />
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
          :page-sizes="[5, 10, 20]"
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
          <el-form-item label="评论内容">
            <el-input type="textarea" v-model="form.text" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="评论用户id">
            <el-input v-model="form.userid" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="评论展品id">
            <el-input v-model="form.exhid" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="评论展览id">
            <el-input v-model="form.zlid" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="评论新闻id">
            <el-input v-model="form.newid" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="评论时间">
            <el-date-picker v-model="form.date" value-format="YYYY-MM-DD HH:mm:ss" type="date" style="width: 80%" clearable></el-date-picker>
          </el-form-item>
          <el-form-item label="评论类型">
            <el-input v-model="form.type" style="width: 80%"/>
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
  name: 'CmntAdmin',
  data() {
    return {
      form: {},
      dialogVisible: false,
      search: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      keyWord: 'text',
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
    load(){
      console.log(this.keyWord,this.search)
      request.get("/cmnt",{
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
    },
    save() {
      if(this.form.id){//更新
        request.put("/cmnt",this.form).then(res => {
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
        console.log(this.form.date)
        request.post("/cmnt",this.form).then(res => {
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
    },
    handleDelete(id){
      request.delete("/cmnt/" + id).then(res =>{
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