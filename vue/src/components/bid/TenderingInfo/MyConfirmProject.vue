<template>
    <div>
      <el-button icon="el-icon-refresh" size="small" type="primary" @click="refresh">刷新</el-button>
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
            property="tenderid"
            label="招标商"
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
        name: "MyConfirmProject",
      data(){
          return{
            projectList:'',
            supplierList:'',
            total: 0,
            page: 1,
            size: 10,
          }
      },
      mounted(){
          this.initSupplierList()
      },
      methods:{
        initSupplierList(){
          this.getRequest('/bid/getSupplierBySupplierId?page='+this.page+'&size='+this.size).then(resp=>{
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
        refresh(){
          this.initSupplierList();
        }
      }
    }
</script>

<style scoped>

</style>
