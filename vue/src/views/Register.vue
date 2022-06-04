<template>
  <div style="width: 100%;height: 100vh;background-color: #bfdcec;overflow: hidden">
    <div style="width: 400px;margin: 150px auto;background-color: white">
      <div style="color: #cccccc;font-size: 30px;text-align: center;padding: 30px">闲置物品交易平台</div>
      <el-form :model="form" style="width: 300px;height: 250px;margin:auto;">
        <el-form-item>
          <el-input :prefix-icon="Avatar" v-model="form.username" placeholder="  请输入用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input :prefix-icon="Lock" v-model="form.password" placeholder="  请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-input :prefix-icon="Lock" v-model="form.confirm" placeholder="  请再次输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button style="width: 40%;margin: auto" type="primary" @click="register">注册</el-button>
        </el-form-item>
        <el-form-item style="display: flex">
          <el-link @click="backstage" style="color: #7faec7">后台登录</el-link>
          <el-link @click="login" style="color: #7faec7;margin-left: auto">登录</el-link>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {Avatar,Lock} from "@element-plus/icons-vue";
import request from "@/utils/request";

export default {
  name: "Login",
  data(){
    return{
      form:{},
      Avatar,
      Lock,
    }
  },
  methods:{
    login(){
      this.$router.push("/login")
    },
    backstage(){
      this.$router.push("/backstage")
    },
    register(){
      if(this.form.password != this.form.confirm){
        this.$message({
          type:"error",
          message:"两次密码不一致"
        })
      }else
        request.post("/user/register",this.form).then(res=>{
        if(res.code =='0'){
          this.$message({
            type:"success",
            message:"注册成功"
          })
          this.$router.push("/login")//登录成功后的页面跳转
        } else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },
  }
}
</script>

<style scoped>

</style>