<template>
 <div>
   <div>
     <el-button size="small" type="primary" icon="el-icon-refresh" @click="refresh">刷新</el-button>
   </div>
   <div style="margin-top: 10px;border: 1px solid lightblue">
     <el-table
       ref="singleTable"
       stripe
       border
       size="mini"
       :data="applyFormList"
       highlight-current-row
       style="width: 100%">
       <el-table-column
         type="index"
         align="center">
       </el-table-column>
       <el-table-column
         property="project.projectname"
         label="项目名称"
         align="center">
       </el-table-column>
       <el-table-column
         property="project.code"
         label="招标编号"
         align="center">
       </el-table-column>
       <el-table-column
         property="supplier.userid"
         label="供应商">
       </el-table-column>
       <el-table-column
         property="createdon"
         label="申请时间">
       </el-table-column>
       <el-table-column
         property="approvestate"
         label="审核状态"
         align="center">
         <template slot-scope="scope">
           <el-tag type="info" v-if="scope.row.approvestate == 1" effect="dark">未审批</el-tag>
           <el-tag type="" v-if="scope.row.approvestate == 2" effect="dark">审批中</el-tag>
           <el-tag type="success" v-if="scope.row.approvestate == 3" effect="dark">已审批</el-tag>
           <el-tag type="danger" v-if="scope.row.approvestate == 4" effect="dark">已拒绝
           </el-tag>
         </template>
       </el-table-column>
       <el-table-column
         property="content"
         label="备注"
         align="center">
       </el-table-column>
       <el-table-column
         label="操作"
         align="center">
         <template slot-scope="scope">
           <el-button size="small" type="success" @click="agree(scope.row)">同意</el-button>
           <el-button size="small" type="danger" @click="refused(scope.row)">拒绝</el-button>
         </template>
       </el-table-column>
     </el-table>
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
 </div>
</template>

<script>
  export default {
    name: "SupplierBuyBookList",
    data() {
      return {
        applyFormList: [],
        total: 0,
        page: 1,
        size: 10,
      }
    },
    mounted() {
      this.initApplyForm();
    },
    methods: {
      initApplyForm() {
        this.getRequest('/tendering/getApplyForm?page=' + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.total = resp.total;
            this.applyFormList = resp.data;
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
      refresh() {
        this.initApplyForm();
      },
      agree(data) {
        if(data.approvestate != '2'){
          this.$message.error("不允许重复审核!");
          return false;
        }
        this.getRequest('/tendering/changeApplyFormStatus?approvestate=3&applyFormid='+data.urid).then(resp=>{
          if(resp){
            this.initApplyForm();
          }
        })
      },
      refused(data) {
        if(data.approvestate != '2'){
          this.$message.error("不允许重复审核!");
          return false;
        }
        this.getRequest('/tendering/changeApplyFormStatus?approvestate=4&applyFormid='+data.urid).then(resp=>{
          if(resp){
            this.initApplyForm();
          }
        })
      },
      refresh(){
        this.initApplyForm();
      }
    }
  }
</script>

<style scoped>

</style>
