<template>
  <div>
    <div>
      <el-input size="small" placeholder="请输入公告名称" prefix-icon="el-icon-search" style="width: 300px"></el-input>
      <el-button size="small" type="primary" icon="el-icon-search">搜索</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table
        width="100%"
        border
        stripe
        size="small"
        :header-cell-style="headClass"
        :data="noticeData"
        >
        <el-table-column
          type="index"
          align="center"
          width="50">
        </el-table-column>
        <el-table-column
          prop="name"
          align="center"
          label="公告标题"
        >
        </el-table-column>
        <el-table-column
          prop="code"
          align="center"
          label="招标编号"
        >
        </el-table-column>
        <el-table-column
          prop="tendingid"
          align="center"
          label="招标商">
        </el-table-column>
        <el-table-column
          prop="tendingid"
          align="center"
          label="招标地点">
        </el-table-column>
        <el-table-column
          prop="time"
          align="center"
          label="投标截止时间">
        </el-table-column>
        <el-table-column
          prop="startdate"
          align="center"
          label="开始时间">
        </el-table-column>
        <el-table-column
          prop="enddate"
          align="center"
          label="结束时间 ">
        </el-table-column>
        <el-table-column
          prop="content"
          align="center"
          label="内容 ">
        </el-table-column>
        <el-table-column
          prop="createdon"
          align="center"
          label="申请时间 ">
        </el-table-column>
        <el-table-column
          prop="updatedon"
          align="center"
          label="发布时间 ">
        </el-table-column>
        <el-table-column
          prop="approvestate"
          label="审核状态"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag type="info" v-if="scope.row.approvestate == 1" effect="dark">未审批</el-tag>
            <el-tag type="primary" v-if="scope.row.approvestate == 2" effect="dark">审批中</el-tag>
            <el-tag type="success" v-if="scope.row.approvestate == 3" effect="dark">已审批</el-tag>
            <el-tag type="danger" v-if="scope.row.approvestate == 4" style="margin-left: 30px" effect="dark">已拒绝
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="cancelstate"
          label="作废状态"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag type="info" v-if="scope.row.cancelstate == 1" effect="dark">未作废</el-tag>
            <el-tag type="" v-if="scope.row.cancelstate == 2" effect="dark">作废中</el-tag>
            <el-tag type="danger" v-if="scope.row.cancelstate == 3" effect="dark">已作废</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          label="操作"
          width="200px"
        >
          <template slot-scope="scope">
          <el-button size="small" type="primary" @click="doApprove(scope.row)">送审</el-button>
          <el-button size="small" type="primary">查看详情</el-button>
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
</template>

<script>
  export default {
    name: "MyNotice",
    data() {
      return {
        noticeData: [],
        total: 0,
        page: 1,
        size: 10,
      }
    },
    mounted(){
      this.init()
    },
    methods: {
      headClass() {
        return 'background:lightblue;letter-spacing:3px;font-weight:bolder;font-size:14px;font-family:楷体'
      },
      init(){
        this.getRequest('/notice/getNoticeByCode?page='+this.page+'&size='+this.size).then(resp=>{
          if(resp){
            this.noticeData = resp.data;
            this.total = resp.toyal;
          }
        })
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.init();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.init();
      },
      doApprove(data){
        if(data.approvestate == '2'){
          this.$message.error("该公告已经在审批中，不允许重复送审！");
          return false;
        }
        if(data.approvestate == '3' || data.approvestate == '4' ){
          this.$message.error("该公告已经审批，不允许重复送审！");
          return false;
        }
        this.getRequest('/notice/doApprove?urid='+data.urid).then(resp=>{
          if(resp){
            this.init();
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
