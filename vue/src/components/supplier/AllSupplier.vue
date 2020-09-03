<template>
  <div>
    <div style="display: flex;justify-items: flex-end">
      <el-input prefix-icon="el-icon-search" size="small" placeholder="请输入供应商名称"
                style="width: 400px;margin-right: 10px"></el-input>
      <el-button icon="el-icon-search" type="primary" size="small">搜索</el-button>
    </div>
    <div style="margin-top: 20px">
      <el-table
        :data="supplierData"
        border
        stripe
        size="small"
        style="width: 100%;"
        :header-cell-style="headClass"
      >
        <el-table-column
          label="序号"
          type="index"
          align="center"
          width="50">
        </el-table-column>
        <el-table-column
          prop="userid"
          align="center"
          label="账号"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          align="center"
          label="名称"
        >
        </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          label="地址"
        >
        </el-table-column>
        <el-table-column
          prop="typeid"
          align="center"
          label="类型">
        </el-table-column>
        <el-table-column
          prop="bidcount"
          align="center"
          label="中标次数">
        </el-table-column>
        <el-table-column
          prop="email"
          align="center"
          label="邮箱">
        </el-table-column>
        <el-table-column
          prop="telephone"
          align="center"
          label="联系方式">
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
    name: "AllSupplier",
    data(){
      return{
        supplierData:[],
        total: 0,
        page: 1,
        size: 10,
      }
    },
    mounted(){
      this.initSupplier()
    },
    methods:{
      headClass(){
        return 'background:lightblue;letter-spacing:3px;font-weight:bolder;font-size:14px;font-family:楷体'
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initSupplier();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initSupplier();
      },
      initSupplier(){
        this.getRequest('/supplier/queryByApproveAndCancel').then(resp=>{
          if(resp){
            this.supplierData = resp.data;
            this.total =resp.total;
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
