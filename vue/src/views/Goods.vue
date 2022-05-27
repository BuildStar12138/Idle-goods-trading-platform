<template>
  <div style="padding: 10px;" >
    <div style="margin-left:1%;margin-right:1%">
        <div v-if="reFresh">
          <el-row>
          <el-col v-model="tableData" v-for="(item) in tableData" :span="6" :offset="1" >
            <div style="margin-top:15px;">
              <el-card :body-style="{padding: '0px',width:'200px',height:'350px'}"
                       shadow="hover" @click.native ="details(item.id)">
                <el-image
                    style="width: 260px; height: 200px;padding-left: 20px;padding-right: 20px"
                    :src="item.cover"
                    :preview-src-list="[item.cover]"
                    fit="cover"
                    preview-teleported="true"
                />
                <div>
                  <div style="padding-left: 10px;margin-top: 10px;">
                    <strong>{{item.title}}</strong>
                  </div>
                  <div style="padding-left: 10px;margin-top: 10px">
                    ¥&nbsp;&nbsp;{{item.price}}
                  </div>
                  <div style="padding-left: 10px;margin-top: 10px">
                    {{item.author}}
                  </div>
                  <div style="padding-left: 10px;margin-top:10px;font-size: small;padding-bottom: 0px">
                    {{item.time}}
                  </div>
                </div>
              </el-card>
            </div>
          </el-col>
          </el-row>
        </div>
      <div style="margin-left:35%">

    <div style="margin: 10px 10px">
      <el-pagination
          :currentPage="currentPage4"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>
    </div>

  </div>
      </div>
      </div>
</template>

<script>

let editor;
import request from "@/utils/request";
export default {
  name: 'News',
  props:['search'],
  components: {
  },
  data(){
    return {
      form:{},
      dialogVisible:false,
      vis:false,
      // search:'',
      total:0,
      currentPage4:1,
      pageSize: 10,
      tableData:[],
      detail:{},
      reFresh:true,
    }
  },
  created(){
    this.load()
  },
  watch:{
    search(){
      console.log("监测到")
      this.load()
    }
  },
  methods:{
    details(id){
      sessionStorage.setItem("detailId",id)
      this.$router.push({path:'/details'})
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
        console.log(this.tableData)
        this.total = res.data.total
        console.log(this.total)
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
  }
}
</script>
