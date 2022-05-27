<template>
  <div style="width: 100%;height: 100vh;background-color: green;overflow: hidden">
    <div style="width: 400px;margin: 150px auto">
      <div style="color: #cccccc;font-size: 30px;text-align: center;padding: 30px">欢迎注册</div>
      <el-form :model="form">
        <el-form-item>
          <el-input :prefix-icon="Avatar" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input :prefix-icon="Lock" v-model="form.password" show-password />
        </el-form-item>
        <el-form-item>
          <el-input :prefix-icon="Lock" v-model="form.confirm" show-password />
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
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