<template>
  <div>
    <div v-if="showList">
      <div style="display: flex;justify-items: flex-end">
        <el-input prefix-icon="el-icon-search" size="small" placeholder="请输入公告标题名称" style="width: 400px;margin-right: 10px"></el-input>
        <el-button icon="el-icon-search" type="primary" size="small">搜索</el-button>
      </div>
      <div>
        <el-table
          :data="NoticeData"
          border
          stripe
          style="width: 100%;margin-top: 20px"
        >
          <el-table-column
            prop="name"
            align="center"
            label="公告名称"
          >
          </el-table-column>
          <el-table-column
            prop="content"
            align="center"
            label="公告内容"
          >
          </el-table-column>
          <el-table-column
            prop="code"
            align="center"
            label="招标编号">
          </el-table-column>
          <el-table-column
            prop="tendingid"
            align="center"
            label="招标负责人">
          </el-table-column>
          <el-table-column
            prop="startdate"
            align="center"
            label="开始时间">
          </el-table-column>
          <el-table-column
            prop="enddate"
            align="center"
            label="结束时间">
          </el-table-column>
          <el-table-column
            prop="time"
            align="center"
            label="投标截至时间">
          </el-table-column>
          <el-table-column label="操作"  align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="queryDetail(scope.row)"
              >查看详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="display: flex;justify-content: flex-end;margin-top: 10px">
        <el-pagination
          background
          @current-change="currentChange"
          @size-change="sizeChange"
          layout="sizes,prev,pager,next,jumper,->,total,slot"
          :total="total">
        </el-pagination>
      </div>
    </div>
    <div v-if="showDetail">
      <div>
        <el-button size="small" type="primary" @click="back">返回</el-button>
      </div>
      <div style="border: 1px solid lightblue;padding: 5px 10px;margin: 10px 0px;">
        <el-form :inline="true" :model="NoticeObject" class="demo-form-inline" label-position="right">
          <el-row>
            <el-col :span="8">
              <el-form-item label="公告标题">
                <el-input v-model="NoticeObject.name" placeholder="公告标题" disabled size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="项目">
                <el-input v-model="NoticeObject.projectid" placeholder="项目" disabled size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="招标编号">
                <el-input v-model="NoticeObject.code" placeholder="招标编号" disabled size="small"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="招标商">
                <el-input v-model="NoticeObject.tendingid" placeholder="招标编号" disabled size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="投标截至时间">
                <el-input v-model="NoticeObject.time" placeholder="投标截至时间" disabled size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="招标开始时间">
                <el-input v-model="NoticeObject.startdate" placeholder="招标开始时间" disabled size="small"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="招标截至时间">
                <el-input size="small" v-model="NoticeObject.enddate" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="公告内容">
                <el-input type="textarea" v-model="NoticeObject.content"  :rows="8"
                          resize="none" style="width: 800px" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
  </div>

</template>

<script>
  export default {
    name: "AllNotice",
    data(){
      return{
        NoticeData:[],
        total:0,
        page:1,
        size:10,
        showList:true,
        showDetail:false,
        NoticeObject:{
          urid:'',
          code: '',
          tendingid: '',
          position: '',
          time: '',
          projectid: '',
          startdate: '',
          enddate: '',
          createdby: '',
          createdon: '',
          updatedby: '',
          updatedon: '',
          approvestate: '',
          cancelstate: '',
          content: '',
          name: '',
          processInstenceid: ''
        },
      }
    },
    mounted(){
      this.initNotice()
    },
    methods:{
      sizeChange(currentSize){
        this.size = currentSize;
        this.initNotice();
      },
      currentChange(currentPage){
        this.page = currentPage;
        this.initNotice();
      },
      initNotice(){
        this.getRequest("/notice/queryNoticeList"+"?page="+this.page+'&size='+this.size).then(resp=>{
          if(resp){
            this.NoticeData = resp.data;
            this.total = resp.total;
          }
        })
      },
      queryDetail(data){
        this.showList = false;
        this.showDetail = true;
        Object.assign(this.NoticeObject, data);
      },
      back(){
        this.showList = true;
        this.showDetail = false;
      }
    }
  }
</script>

<style scoped>

</style>
