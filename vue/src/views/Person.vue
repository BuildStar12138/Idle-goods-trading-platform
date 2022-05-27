<template>
  <div>
    <!-- 头部   -->
    <Header_c/>
    <!--  主体  -->
    <div style="display: flex">
      <!--   侧边栏   -->
      <Aside_c/>
      <!--   内容区   -->
      <div style="width: 1200px;height: 800px">
        <!--   展示区     -->

        <div>
          <div style="padding-left: 20px;padding-top: 20px;display: flex;">
            <div>
              <el-avatar shape="square" :size="100" :fit="fit"
                         src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
              />
            </div>
            <div>
              <div>&nbsp;&nbsp;&nbsp;<strong>{{this.form.nickName}}</strong></div>
              <div style="font-size: small;margin-top: 10px">&nbsp;&nbsp;&nbsp;&nbsp;地址：{{this.form.address}}</div>
              <el-button style="color: #7faec7;background-color: #bfdcec;font-size: small;
              margin-left: 10px;margin-top: 10px;" @click="dialogVisible=true">
                编辑个人信息
              </el-button>
            </div>
          </div>
        </div>
        <hr style="margin-top: 20px">
        <el-menu
            class="el-menu-demo"
            mode="horizontal"
            style="width: 1200px"
            @select="handleSelect"
        >
          <el-menu-item index="">我发布的</el-menu-item>
        </el-menu>
        <el-row>
          <el-col v-model="tableData" v-for="(item) in tableData" :span="24" :offset="1" style="margin-left: 0px">
            <div style="margin-top:5px;">
              <el-card :body-style="{padding: '0px',width:'1100px',height:'100px'}"
                       shadow="hover">
                <div style="display: flex">
                  <el-image
                      style="width: 130px; height: 100px;"
                      :src="item.cover"
                      :preview-src-list="[item.cover]"
                      fit="cover"
                      preview-teleported="true"
                  />
                  <div style="display:flex;flex-direction: column;margin-left: 10px;padding-top: 5px">
                    <strong>{{item.title}}</strong>
<!--                    <div v-html="item.content" style="margin-top: 5px;font-size: small"></div>-->
                    <br>
                    <div style="margin-top: 5px">{{item.time}}</div>
                    <div style="margin-top: 5px;color: red">¥&nbsp;&nbsp;{{item.price}}</div>
                  </div>
                  <div style="margin-top: 50px;margin-left: auto">
                    <el-popconfirm title="确认删除吗?" @confirm="delete1">
                      <template #reference>
                        <el-button style="background-color: #eeb3b3;color: #b65050" @click="setId(item.id)">
                          删除
                        </el-button>
                      </template>
                    </el-popconfirm>
                  </div>
                  </div>
              </el-card>
            </div>
          </el-col>
        </el-row>

        <el-dialog v-model="dialogVisible" title="个人信息" width="30%">
          <el-form :model="form" label-width="120px">
            <el-form-item label="用户名">
              <el-input v-model="form.username" style="width: 80%"/>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="form.nickName" style="width: 80%"/>
            </el-form-item>
            <el-form-item label="年龄">
              <el-input v-model="form.age" style="width: 80%"/>
            </el-form-item>
            <el-form-item label="性别">
              <div>
                <el-radio v-model="form.sex" label="男">男</el-radio>
                <el-radio v-model="form.sex" label="女">女</el-radio>
                <el-radio v-model="form.sex" label="未知">未知</el-radio>
              </div>
            </el-form-item>
            <el-form-item label="地址">
              <el-input type="textarea" v-model="form.address" style="width: 80%"/>
            </el-form-item>
          </el-form>
          <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">更改</el-button>
      </span>
          </template>
        </el-dialog>

      </div>
      <!--   侧边栏   -->
      <Aside_c/>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Header_c from "@/components/Header_c";
import Aside_c from "@/components/Aside_c";

export default {
  name: "Person",
  components:{
    Aside_c,
    Header_c,
  },
  data() {
    return {
      form: {},
      tableData:[],
      id:{},
      dialogVisible:false
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    this.load()
  },
  methods: {
      load(){
        request.get("/news/author/"+this.form.nickName).then(res => {
          this.tableData = res.data
        })
      },
    delete1(){
      console.log(this.id)
        request.delete("/news/"+this.id).then(res => {
          if(res.code =='0'){
            this.$message({
              type:"success",
              message:"删除成功"
            })
          } else {
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
        })
    },
    setId(id){
      this.id = id;
      console.log(id)
    },
    save(){
      request.put("/user",this.form).then(res => {
        console.log(res)
        if(res.code =='0'){
          this.$message({
            type:"success",
            message:"更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))  // 缓存用户信息
        } else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()//刷新表格
        this.dialogVisible = false
      })
    }
  }
}
</script>

<style scoped>

</style>