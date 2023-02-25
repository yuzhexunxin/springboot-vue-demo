<template>
  <div class="mt-4">
    <el-input
        v-model="search"
        @keyup.enter="load"
        placeholder="请输入关键字"
        style="width: 60%; margin-left: 173px"
        clearable
    >
      <template #prepend>
        <el-select v-model="keyWord" placeholder="分类" style="width: 115px">
          <el-option label="年龄" value="age" />
          <el-option label="性别" value="sex" />
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search" @click="dd"/>
      </template>
    </el-input>
  </div>
<!--  <div class="mt-4">-->
<!--    <el-input-->
<!--        v-model="input3"-->
<!--        placeholder="Please input"-->
<!--        class="input-with-select"-->
<!--    >-->
<!--      <template #prepend>-->
<!--        <el-button :icon="Search" @click="dd"/>-->
<!--      </template>-->
<!--      <template #append>-->
<!--        <el-select v-model="select" placeholder="Select" style="width: 115px" >-->
<!--          <el-option label="Restaurant" value="1" />-->
<!--          <el-option label="Order No." value="2" />-->
<!--          <el-option label="Tel" value="3" />-->
<!--        </el-select>-->
<!--      </template>-->
<!--    </el-input>-->
<!--  </div>-->
</template>

<script >
  import { ref } from 'vue'
  import {Search} from "@element-plus/icons";
  const input1 = ref('')
  const input2 = ref('')
  const input3 = ref('')
  // const keyWord = ref('')
  export default {
    name: "Test",
    data(){
      return{
        keyWord: '',
      }
    },
    methods:{
      dd(a){
        console.log(this.keyWord)
      }
    },
    computed: {
      Search() {
        return Search
      }
    },

  }
</script>

<style>

</style>