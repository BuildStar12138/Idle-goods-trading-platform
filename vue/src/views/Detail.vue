<template>
  <div>
    <div style="padding-left: 20px;padding-top: 20px;display: flex;justify-content: flex-end;">
      <div>
        <el-avatar shape="square" :size="100" :fit="fit"
                   src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        />
      </div>
      <div>
        <div >&nbsp;&nbsp;&nbsp;<strong>{{this.form.author}}</strong></div>
        <br>
        <div style="font-size: small">&nbsp;&nbsp;&nbsp;{{this.form.time}}&nbsp;发布该商品信息</div>
      </div>
      <div style="color: red;margin-left: auto">
        <br>
        <br>
        ¥&nbsp;{{this.form.price}}&nbsp;&nbsp;
        <el-popover
            placement="left"
            title="联系方式"
            :width="300"
            trigger="click"
        >
          {{this.form.contact}}
          <template #reference>
            <el-button style="background-color: #eeb3b3;color: #b65050">联系卖家</el-button>
          </template>
        </el-popover>
        <el-button type="primary" @click="back">返回</el-button>
      </div>
    </div>
  </div>
  <hr style="margin-top: 20px">
  <div style="width: 800px;height: 600px;">
    <div style="padding-left: 20px;padding-top: 20px;font-size: x-large">
      <h4>商品名：{{this.form.title}}</h4>
    </div>
    <div v-html="this.form.content" style="padding-left: 20px;padding-top: 20px;"></div>
    <div style="padding-left: 20px;padding-top: 20px;">
      <el-image
          style="width: 400px; height: 400px"
          :src="this.form.cover"
          :preview-src-list="[this.form.cover]"
          fit="cover"
          preview-teleported="true"
      />
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import { UserFilled } from '@element-plus/icons-vue'

export default {
  name: "Detail",
  data(){
    return {
      form:{},
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      request.get("/news/"+sessionStorage.getItem("detailId"),{
      }).then(res => {
        console.log(res)
        this.form = res.data
      })
    },
    back(){
      this.$router.push({path:'/goods'})
    }
  }
}
</script>

<style scoped>

</style>