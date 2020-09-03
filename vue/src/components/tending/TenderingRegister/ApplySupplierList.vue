<template>
    <div>
      <div>
        <el-select style="width: 250px" placeholder="请选择项目进行查找" v-model="projectId" size="small" >
          <el-option v-for="(project,indexk) in projectList"
                     :label="project.projectname"
                     :value="project.urid"
                     :key="indexk"></el-option>
        </el-select>
        <el-button icon="el-icon-search" size="small" type="primary">查找</el-button>
        <el-button icon="el-icon-refresh" size="small" type="primary" @click="refresh">刷新</el-button>
      </div>
      <div style="margin-top: 10px">
        <el-table
          :data="supplierList"
          border
          stripe
          style="width: 100%">
          <el-table-column
            label="序号"
            type="index"
            width="50"
          align="center">
          </el-table-column>
          <el-table-column
            property="project.projectname"
            label="项目名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            property="project.code"
            label="招标编号"
            align="center"
          >
          </el-table-column>

          <el-table-column
            property="supplier.userid"
            label="供应商名称"
            align="center"
            >
          </el-table-column>
          <el-table-column
            property="createdon"
            label="报名时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            property="supplier.address"
            label="单位"
            align="center"
          >
          </el-table-column>
          <el-table-column
            property="approvestate"
            label="审批状态"
            align="center"
          >
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.approvestate == 1" effect="dark">未审批</el-tag>
              <el-tag type="" v-if="scope.row.approvestate == 2" effect="dark">审批中</el-tag>
              <el-tag type="success" v-if="scope.row.approvestate == 3" effect="dark">已审批</el-tag>
              <el-tag type="danger" v-if="scope.row.approvestate == 4"  effect="dark">已拒绝
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            property="code"
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="agree(scope.row)" >同意</el-button>
              <el-button type="danger" size="mini" @click="refused(scope.row)" >拒绝</el-button>
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
        name: "ApplySupplierList",
      data(){
          return{
            projectId:[],
            projectList:[],
            supplierList:[],
            total: 0,
            page: 1,
            size: 10,
          }
      },
      mounted(){
          this.initProject();
          this.initSupplierList();
      },
      methods:{
        initSupplierList(){
          this.getRequest('/bid/getConfirmByTenderId?page='+this.page+'&size='+this.size).then(resp=>{
            if(resp){
              this.total = resp.total;
              this.supplierList = resp.data;
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
        initProject(){
          this.getRequest('/tendering/getAllApprovedProject?page=' + 1 + '&size=' + 1000).then(resp => {
            if (resp) {
              this.projectList = resp.data;
            }
          });
        },
        agree(data){
          if(data.approvestate!='2'){
            this.$message.error("不允许重复审核！");
            return false;
          }
          this.getRequest('/bid/changeApproveState?urid='+data.urid+'&approvestate=3').then(resp=>{
            if(resp){
              this.initSupplierList();
            }
          })
        },
        refused(data){
          if(data.approvestate!='2'){
            this.$message.error("不允许重复审核！");
            return false;
          }
          this.getRequest('/bid/changeApproveState?urid='+data.urid+'&approvestate=4').then(resp=>{
            if(resp){
              this.initSupplierList();
            }
          })
        },
        refresh(){
          this.initSupplierList();
        }
      }
    }
</script>

<style scoped>

</style>
