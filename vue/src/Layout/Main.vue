<template>
  <div>
    <!-- 头部   -->
    <Header_c @search="search1"/>
    <!--  主体  -->
    <div style="display: flex">
      <!--   侧边栏   -->
      <Aside_c/>
      <!--   内容区   -->
      <div style="width: 1200px;height: 800px">
      <!--    菜单栏    -->
        <MainHeader @search="search1"/>
      <!--   展示区     -->
        <router-view style="flex: 1" :search="search"/>
      </div>
      <!--   侧边栏   -->
      <Aside_c/>
    </div>
  </div>
</template>

<script>
import Header_c from "@/components/Header_c";
import Aside_c from "@/components/Aside_c";
import MainHeader from "@/components/MainHeader";
import Goods from "@/views/Goods";

export default {
  name: "Main",
  components:{
    Goods,
    MainHeader,
    Aside_c,
    Header_c,
  },
  data(){
    return{
      search:''
    }
  },
  methods:{
    search1(data){
      console.log(data)
      this.search=data
      console.log(this.search)
    }
  }
}
</script>

<style scoped>

</style>