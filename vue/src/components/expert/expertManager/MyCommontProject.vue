<template>
  <div>
    <div v-if="showList">
      <div>
        <el-select v-model="status" size="small" placeholder="请选择评标状态">
          <el-option value="0" label="未评标"></el-option>
          <el-option value="1" label="已评标"></el-option>
        </el-select>
        <el-button icon="el-icon-search" type="primary" size="small">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" type="primary" @click="refresh">刷新</el-button>
      </div>
      <div style="margin-top: 10px">
        <el-table
          :data="groupListData"
          stripe
          border
          size="mini"
          style="width: 100%">
          <el-table-column
            prop="project.projectname"
            align="center"
            label="项目名称"
          >
          </el-table-column>
          <el-table-column
            prop="grouplist.name"
            align="center"
            label="专家组名称"
          >
          </el-table-column>
          <el-table-column
            prop="grouplist.count"
            align="center"
            label="专家数量"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            align="center"
            label="评标状态"
          >
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.status == 0"  effect="dark">未评标</el-tag>
              <el-tag type="success" v-if="scope.row.status == 1"  effect="dark">已评标</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="createdon"
            align="center"
            label="创建时间"
          >
          </el-table-column>
          <el-table-column
            prop="createdby"
            align="center"
            label="创建人">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="150px">
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="queryDetail(scope.row)" >查看详情</el-button>
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
        <div><el-button type="primary" size="small" @click="back">返回</el-button></div>
        <div style="margin-top: 10px">
          <el-table
            :data="fileData"
            stripe
            border
            style="width: 100%">
            <el-table-column
              prop="name"
              align="center"
              label="文件名称"
            >
            </el-table-column>
            <el-table-column
              prop="project.projectname"
              align="center"
              label="项目名称"
            >
            </el-table-column>
            <el-table-column
              prop="project.projectcode"
              align="center"
              label="项目编号"
            >
            </el-table-column>
            <el-table-column
              prop="project.code"
              align="center"
              label="招标编号"
            >
            </el-table-column>
            <el-table-column
              prop="createdby"
              align="center"
              label="上传人">
            </el-table-column>
            <el-table-column
              prop="type"
              align="center"
              label="文件类型">
              <template slot-scope="scope">
                <el-tag type="info" v-if="scope.row.type == 1"  effect="dark">招标文件</el-tag>
                <el-tag type="success" v-if="scope.row.type == 2"  effect="dark">投标文件</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="操作"
              width="150px">
              <template slot-scope="scope">
                <el-link :href="href" type="success" @click="download(scope.row)" :underline="false" style="margin-right: 10px">下载</el-link>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "MyCommontProject",
    data(){
      return{
        showList:true,
        showDetail:false,
        groupListData:[],
        status:'',
        total: 0,
        page: 1,
        size: 10,
        projectid:'',
        href:'',
        fileData:[],
      }
    },
    mounted(){
      this.initProject();
    },
    methods:{
      refresh(){
        this.initProject();
      },
      initProject(){
        this.getRequest('/expert/getAllExpertProject?page='+this.page+'&size='+this.size).then(resp=>{
          if(resp){
            this.groupListData = resp.data;
            this.total = resp.total;
          }
        })
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initFile();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initFile();
      },
      queryDetail(data){
        this.showList = false;
        this.showDetail = true;
        this.projectid = data.projectid;
        this.getRequest('/file/getProjectFile?projectid='+this.projectid).then(resp=>{
          if(resp){
            this.fileData = resp.data;
          }
        })
      },
      download(data){
        this.href="http://localhost:8888/file/download?urid="+data.urid;
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
