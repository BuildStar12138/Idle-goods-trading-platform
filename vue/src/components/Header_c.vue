<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <el-link href="http://localhost:9876/" style="width: 200px; padding-left: 20px; font-size: large;font-weight: bold; color: dodgerblue;margin-left: 320px">
      闲置物品交易平台
    </el-link>
    <div style="margin-left: 20px">
      <el-input v-model="search" placeholder="搜闲置" style="width: 300px" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load1()" c>查询</el-button>
    </div>
    <div style="margin-left: 40px">
      <el-button type="primary" @click="add">我要出售</el-button>
    </div>
    <div style="width: 100px;margin-left: 40px">
      <el-dropdown style="margin-top: 18px">
    <span class="el-dropdown-link">
      {{ user.nickName }}
      <i class="el-icon-arrow-down el-icon--right"></i>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
<!--            <el-dropdown-item @click="$router.push('/password')">修改密码</el-dropdown-item>-->
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
  <el-dialog v-model="dialogVisible" title="出售商品信息" width="50%">
    <el-form :model="form" label-width="120px">
      <el-form-item label="标题">
        <el-input v-model="form.title" style="width: 80%"/>
      </el-form-item>
      <el-form-item label="类型">
        <el-radio-group v-model="form.type">
          <el-radio label="数码">数码</el-radio>
          <el-radio label="家电">家电</el-radio>
          <el-radio label="户外">户外</el-radio>
          <el-radio label="图书">图书</el-radio>
          <el-radio label="其他">其他</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="价格（元）">
        <el-input v-model="form.price" style="width: 50%"/>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="form.contact" style="width: 50%"/>
      </el-form-item>
      <el-form-item label="封面">
        <el-upload ref="upload" action="/api/file/upload" :on-success="filesUploadSuccess">
          <el-button type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="物品信息">
        <div id="div1"></div>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import E from "wangeditor";
import request from "@/utils/request";
import Goods from "@/views/Goods";


let editor
export default {
  name: "Header_c",
  components:{
    Goods
  },
  data() {
    return {
      user: {},
      form:{},
      dialogVisible:false,
      search:'',
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  },
  methods:{
    filesUploadSuccess(res){
      console.log(res)
      this.form.cover = res.data
    },
    load1(){
      this.$emit('search',this.search)
      console.log(this.search)
      // Goods.methods.setSearch(this.search)
    },
    add(){
      this.dialogVisible = true
      this.form = {}
      this.form.cover="http://localhost:9090/file/默认图片.webp"
      this.$nextTick(()=>{
        //富文本编辑器
        editor = new E('#div1');//获取组件并构造编辑器
        editor.create(); // 创建富文本实例
      })
    },
    save(){
      this.form.content = editor.txt.html()//获取编辑器里面的值
      let userStr = sessionStorage.getItem("user")||"{}"
      let user = JSON.parse(userStr)
      this.form.author = user.nickName
      request.post("/news",this.form).then(res => {
        console.log(res)
        if(res.code =='0'){
          this.$message({
            type:"success",
            message:"新增成功"
          })
        } else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        Goods.methods.load()
        this.dialogVisible = false
      })
      }
    },
}
</script>

<style scoped>

</style>