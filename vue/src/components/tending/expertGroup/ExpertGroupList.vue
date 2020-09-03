<template>
  <div>
    <div>
      <el-button size="small" type="primary" icon="el-icon-refresh" @click="refresh">刷新</el-button>
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
          prop="name"
          align="center"
          label="专家组名称"
        >
        </el-table-column>
        <el-table-column
          prop="count"
          align="center"
          label="专家数量"
        >
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
            <el-button type="success" size="mini" @click="queryDetail(scope.row)" >查看成员</el-button>
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
    name: "ExpertGroupList",
    data() {
      return {
        total: 0,
        page: 1,
        size: 10,
        groupListData:[]
      }
    },
    mounted() {
      this.initGroupList();
    },
    methods: {
      initGroupList() {
        this.getRequest('/tendering/getExpertGroupList?page='+this.page+'&size='+this.size).then(resp=>{
          if(resp){
            this.groupListData = resp.data;
            this.total = resp.total;
          }
        })
      },
      refresh() {
        this.initGroupList();
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

      }
    }
  }
</script>

<style scoped>

</style>
