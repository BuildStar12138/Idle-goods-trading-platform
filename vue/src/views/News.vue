<template>
  <div style="padding: 10px;" >
    <!--  功能区域  -->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">我要出售</el-button>
    </div>

    <!--  搜索区域  -->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load" c>查询</el-button>
    </div>
    <el-table :data="tableData" stripe style="width: 100%" fit="true"
              :default-sort="{ prop: 'time', order: 'descending' }">
<!--      <el-table-column prop="id" label="ID" sortable fit="true" style="width: 10px"/>-->
      <el-table-column label="物品图片" width="150">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :preview-src-list="[scope.row.cover]"
              fit="cover"
              preview-teleported="true"
          />
        </template>
      </el-table-column>
      <el-table-column prop="title" label="物品名称" width="100"/>
      <el-table-column prop="author" label="卖家" width="80"/>
      <el-table-column prop="time" label="发布时间" width="180"/>
      <el-table-column label="物品信息" width="80">
        <template #default="scope">
          <el-button type="text" size="small" @click="details(scope.row)">详情</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="contact" label="联系方式" width="180"/>
      <el-table-column prop="price" label="价格（元）" width="180"/>
      <el-table-column fixed="right" label="操作" width="180">
        <template #default="scope">
<!--          <el-button type="text" size="small" @click="details(scope.row)">详情</el-button>-->
          <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="text" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          :currentPage="currentPage4"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>

      <el-dialog v-model="dialogVisible" title="提示" width="50%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="标题">
            <el-input v-model="form.title" style="width: 80%"/>
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
      <el-dialog v-model="vis" title="详情" width="50%">
        <el-card>
          <div v-html="detail.content"></div>
        </el-card>
      </el-dialog>
    </div>

  </div>
</template>

<script>

let editor;
import request from "@/utils/request";
import E from 'wangeditor'
export default {
  name: 'News',
  components: {
  },
  data(){
    return {
      form:{},
      dialogVisible:false,
      vis:false,
      search:'',
      total:0,
      currentPage4:1,
      pageSize: 10,
      tableData:[],
      detail:{},
    }
  },
  created(){
    this.load()
  },
  methods:{
    details(row){
      this.detail=row
      this.vis=true
    },
    filesUploadSuccess(res){
      console.log(res)
      this.form.cover = res.data
    },
    load(){
      request.get("/news",{
        params:{
          pageNum: this.currentPage4,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
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
      if(this.form.id){
        request.put("/news",this.form).then(res => {
          console.log(res)
          if(res.code =='0'){
            this.$message({
              type:"success",
              message:"更新成功"
            })
          } else {
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()//刷新表格
          this.dialogVisible = false
        })
      } else {
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
          this.load()//刷新表格
          this.dialogVisible = false
        })
      }
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(()=>{
        //富文本编辑器
        editor = new E('#div1');//获取组件并构造编辑器
        editor.create(); // 创建富文本实例
        editor.txt.html(row.content)
      })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage4 = pageNum
      this.load()
    },
    handleDelete(id){
      console.log(id)
      request.delete("/news/"+id).then(res => {
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
  }
}
</script>
