<template>

  <div>
    <div v-if="info">
      <div>
        <div style="margin-top: 20px;">
          <el-button @click="createModel" type="primary">创建一个空模型</el-button>
          <el-input placeholder="模型名称/模糊查询" style="width: 300px;" v-model="search.modelName" size="small"  prefix-icon="el-icon-search"></el-input>
          <el-button icon="el-icon-search" style="width: 100px;margin-left: 20px" type="primary" @click="query" size="small">搜索</el-button>
        </div>
        <div>
          <el-table
            :data="modelList"
            style="width: 100%;margin-top: 10px"
            size="small"
            border>
            <el-table-column prop="id" label="ID" v-if="1==2"></el-table-column>
            <el-table-column prop="name" label="模型名称"></el-table-column>
            <el-table-column prop="key" label="模型标识" width="300"></el-table-column>
            <el-table-column prop="deploymentId" label="部署ID"></el-table-column>
            <el-table-column label="状态" prop="deploymentId" align="center">
              <template slot-scope="scope">
                <el-tag type="info" v-if="scope.row.deploymentId == null" effect="dark">未部署</el-tag>
                <el-tag type="success" v-if="scope.row.deploymentId != null" effect="dark">已部署</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" ></el-table-column>
            <el-table-column prop="lastUpdateTime" label="修改时间" ></el-table-column>
            <el-table-column label="操作" width="300">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" @click="handleDraw(scope.row)">流程设计</el-button>
                <el-button size="mini" type="success" @click="handleDeploy(scope.row)">部署发布</el-button>
                <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: flex-end">
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
      <el-dialog
        title="创建模型"
        :visible.sync="dialogVisible"
        width="30%">
        <el-form ref="form" :model="activitiModel" style="width: 500px">
          <el-form-item label="模型名称">
            <el-input v-model="activitiModel.name" placeholder="模型名称" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="模型标识">
            <el-input v-model="activitiModel.key" placeholder="模型标识" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="流程描述">
            <el-input type="textarea" v-model="activitiModel.desc" placeholder="流程描述" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="dialogVisible=false" style="margin-left: 200px">取消</el-button>
            <el-button type="primary" @click="onSubmit">确定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <div style="height: 800px;" v-if="isdraw">
      <iframe :src="src"  scrolling="no" frameborder="0"
              style="width:100%;height: 800px">
      </iframe>
    </div>
  </div>
</template>

<script>
  export default {
    name: "ActivitiIndex",
    data() {
      return {
        activitiModel: {
          name: '',
          key: '',
          desc: ''
        },
        dialogVisible: false,
        modelList: [],
        info: true,
        isdraw: false,
        src: '',
        search:{
          modelName:'',
        },
        total:0,
        page:1,
        size:10,
      }

    },
    created() {
      // 用_this 接收 this 对象,否则this指向会改变。
      let _this = this
      window.addEventListener('message', function (event) {
        _this.info = true
        _this.isdraw = false;
        _this.initModelList();
      })
    },
    mounted() {
      this.initModelList()
    },
    methods: {
      sizeChange(currentSize){
        this.size = currentSize;
        this.initModelList();
      },
      currentChange(currentPage){
        this.page = currentPage;
        this.initModelList();
      },
      query(){
        if(!this.search.modelName){
          this.$message.error("模型名称不能为空！")
          return false
        }else{
          this.page=1
          this.size=10
          this.getRequest('/activiti/query/'+this.search.modelName+"?page="+this.page+'&size='+this.size).then(resp=>{
            if(resp){
              this.modelList =resp;
              this.total=resp.total;
            }
          })
        }
      },
      handleDraw(data) {
        this.info = false;
        this.isdraw = true;
        this.src = 'http://localhost:8888/activiti/drowModel/' + data.id;
      },
      handleDeploy(data) {
        this.$confirm('你确认要部署模型名称为【' + data.name + '】是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'success'
        }).then(() => {
          this.getRequest('/activiti/deployment/' + data.id).then(resp => {
            if (resp) {
              this.initModelList()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消部署'
          });
        });
      },
      handleDelete(data) {
        this.$confirm('你确认要删除模型名称为【' + data.name + '】是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'error'
        }).then(() => {
          this.deleteRequest('/activiti/delete/' + data.id).then(resp => {
            if (resp) {
              this.initModelList();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      initModelList() {
        this.postRequest('/activiti/getAllModel?page='+this.page+'&size='+this.size, this.activitiModel).then(resp => {
          if (resp) {
            this.modelList = resp.data;
            this.total = resp.total
          }
        })
      },
      createModel() {
        this.dialogVisible = true
      },
      onSubmit() {
        this.postRequest('/activiti/create', this.activitiModel).then(resp => {
          if (resp) {
            this.dialogVisible=false;
            this.activitiModel.name='';
            this.activitiModel.key='';
            this.activitiModel.desc='';
            this.initModelList();
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
