<template>
  <div>
    <div style="display: flex;justify-items: auto;">
      <div>
        <el-input prefix-icon="el-icon-search" size="small" style="width: 200px;margin-top: 10px;"
                  placeholder="请输入文件名"></el-input>
        <el-button icon="el-icon-search" size="small" type="primary" style="margin-left: 10px" >搜索</el-button>
        <el-button type="primary" size="small" @click="refresh" icon="el-icon-refresh">刷新</el-button>
      </div>
    </div>
    <div style="margin-top: 30px">
      <el-table
        :data="fileData"
        stripe
        border
        :header-cell-style="headClass"
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
          prop="user.code"
          align="center"
          label="上传人">
        </el-table-column>
        <el-table-column
          prop="uploadtime"
          align="center"
          label="上传时间">
        </el-table-column>
        <el-table-column
          prop="download"
          align="center"
          label="下载次数">
        </el-table-column>
        <el-table-column
          align="center"
          label="操作"
        width="180px">
          <template slot-scope="scope">
            <el-link :href="href" type="success" @click="download(scope.row)" :underline="false" style="margin-right: 10px">下载附件</el-link>
            <el-button type="danger" size="mini" @click="deleteFile(scope.row)" icon="el-icon-delete">删除</el-button>
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
    name: "MyTenderingFileList",
    data() {
      return {
        href:'',
        fileData: [],
        selectProject: '',
        projectList: '',
        centerDialogVisible: false,
        total: 0,
        page: 1,
        size: 10,
      }
    },
    mounted() {
      this.initFile();
    },
    methods: {
      refresh(){
        this.initFile();
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initFile();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initFile();
      },
      initFile() {
        this.getRequest('/file/getAllFile?page=' + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.fileData = resp.data;
            this.total = resp.total;
          }
        })
      },
      headClass() {
        return 'background:lightblue;letter-spacing:3px;font-weight:bolder;font-size:18px;font-family:楷体'
      },

      download(data){
        this.href="http://localhost:8888/file/download?urid="+data.urid;
      },
      deleteFile(data){
        this.$confirm('此操作将永久删除【' + data.name + '】是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/file/deleteFile/?urid=' + data.urid).then(resp => {
            if (resp) {
              this.initFile();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

    }

  }
</script>

<style scoped>

</style>
