<template>
      <div>
        <el-button size="small" type="primary" icon="el-icon-refresh" @click="refresh">刷新</el-button>
        <div style="margin-top: 10px">
          <el-table
            size="mini"
            ref="singleTable"
            stripe
            border
            :data="applyFromData"
            highlight-current-row
            style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              property="project.projectname"
              align="center"
              label="项目名称"
              >
            </el-table-column>
            <el-table-column
              property="project.code"
              label="招标编号"
              align="center">
            </el-table-column>
            <el-table-column
              property="project.tenderingid"
              label="招标人"
              align="center">
            </el-table-column>
            <el-table-column
              property="approvestate"
              label="审核状态"
              align="center">
              <template slot-scope="scope">
                <el-tag type="info" v-if="scope.row.approvestate == 1" effect="dark">未审批</el-tag>
                <el-tag type="" v-if="scope.row.approvestate == 2" effect="dark">审批中</el-tag>
                <el-tag type="success" v-if="scope.row.approvestate == 3" effect="dark">已审批</el-tag>
                <el-tag type="danger" v-if="scope.row.approvestate == 4"  effect="dark">已拒绝
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              property="content"
              label="备注"
            align="center">
            </el-table-column>
            <el-table-column
              property="createdon"
              label="申请时间"
              align="cenetr">
            </el-table-column>
            <el-table-column
              label="操作"
              align="center">
              <template slot-scope="scope">
                <el-link :href="href" type="success" @click="download(scope.row)" :underline="false" >下载附件</el-link>
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
        name: "BidBookList",
      data(){
          return{
            applyFromData:[],
            total: 0,
            page: 1,
            size: 10,
            href:'',
          }
      },
      mounted(){
          this.initApplyForm();
      },
      methods:{
        initApplyForm(){
          this.getRequest('/bid/getApplyFormList?page='+this.page+'&size='+this.size).then(resp=>{
            if(resp){
              this.total = resp.total;
              this.applyFromData = resp.data;
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
        refresh(){
          this.initApplyForm();
        },
        download(data){
          console.log(data.approvestate)
          if(data.approvestate == '2'){
            this.$message.error("您的申请还在审核中!不允许下载文件");

          }
          if(data.approvestate =='4'){
            this.$message.error("您的申请未通过!不允许下载文件");

          }
          if(data.approvestate=='3'){
            this.href="http://localhost:8888/bid/download?projectid="+data.projectid;
          }
        }
      }

    }
</script>

<style scoped>

</style>
