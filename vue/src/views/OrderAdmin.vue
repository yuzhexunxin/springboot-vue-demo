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
            <el-option label="用户id" value="userid" />
            <el-option label="订单号" value="ordercode" />
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
      <el-table-column prop="userid" label="预约用户id" align="center" />
      <el-table-column prop="name" label="预约用户" align="center" />
      <el-table-column prop="date" label="预约时间" align="center" />
      <el-table-column prop="idcard" label="身份证" align="center" />
      <el-table-column prop="phone" label="手机号" align="center" />
      <el-table-column prop="nump" label="预约人数" align="center" />
      <el-table-column prop="statu" label="预约状态" align="center" />
      <el-table-column prop="ordercode" label="订单号" align="center" />
      <el-table-column prop="dodate" label="订单时间" align="center" />
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
          <el-form-item label="预约用户id">
            <el-input v-model="form.userid" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="预约用户">
            <el-input v-model="form.name" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="预约时间">
            <el-date-picker v-model="form.date" value-format="YYYY-MM-DD HH:mm:ss" type="date" style="width: 80%" clearable></el-date-picker>
          </el-form-item>
          <el-form-item label="身份证">
            <el-input v-model="form.idcard" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="form.phone" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="预约人数">
            <el-input v-model="form.nump" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="预约状态">
            <el-input v-model="form.statu" style="width: 80%"/>
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
      keyWord: 'userid',
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
      request.get("/order",{
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
        request.put("/order",this.form).then(res => {
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
        let orderDate = new Date()
        let timeCode = orderDate.getMonth()+ "" + orderDate.getDate()+ "" + orderDate.getHours()+ "" + orderDate.getMinutes()+ "" + orderDate.getMilliseconds()+ ""
        this.form.ordercode = ((Math.floor(Math.random() * (999999999 - 100000000 + 1) ) + 100000000)+ "") + timeCode + this.form.userid
        this.form.dodate = orderDate.toLocaleString().replaceAll("/","-")
        request.post("/order",this.form).then(res => {
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
      request.delete("/order/" + id).then(res =>{
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