<template>
  <el-menu
      class="el-menu-demo"
      mode="horizontal"
      style="width: 1200px"
      @select="handleSelect"
  >
    <el-menu-item index="">全部</el-menu-item>
    <el-menu-item index="数码">数码</el-menu-item>
    <el-menu-item index="家电">家电</el-menu-item>
    <el-menu-item index="户外">户外</el-menu-item>
    <el-menu-item index="图书">图书</el-menu-item>
    <el-menu-item index="其他">其他</el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "MainHeader",
  data(){
    return{
      index1:""
    }
  },
  watch:{
    index1(){
      this.$emit('search',this.index1)
      console.log("监听到index:"+this.index1)
    }
  },
  methods:{
    handleSelect(index){
      this.index1=index
    }
  },
}
</script>

<style scoped>

</style>