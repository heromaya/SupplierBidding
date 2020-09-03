<template>
  <div>
    <el-button type="primary" size="small" @click="submitUpload" icon="el-icon-tickets">保存</el-button>
    <div style="padding: 10px 10px;margin-top: 10px;border: 1px solid lightblue">
      <el-form :model="bookForm" :rules="rules" ref="bookForm">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目名称" prop="projectId">
              <el-select style="width: 250px" placeholder="请选择" v-model="bookForm.projectId" @change="changeProject">
                <el-option v-for="(project,indexk) in projectList"
                :label="project.projectname"
                :value="project.urid"
                :key="indexk"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="招标编号" prop="projectCode">
              <el-input style="width: 250px"  placeholder="请选择项目名称" :disabled="true" v-model="bookForm.projectCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="编制人" prop="writer">
              <el-input style="width: 250px" :disabled="true" v-model="bookForm.writer"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="文件购买金额" prop="amount">
              <el-input style="width: 250px" placeholder="请填写文件购买所需金额"  v-model="bookForm.amount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="编制日期" prop="time">
              <el-date-picker
                type="date"
                :disabled="true"
                placeholder="选择日期" class="inputInfo"
                value-format="yyyy-mm-dd" style="width: 250px" v-model="bookForm.time">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="标书名称" prop="bookName">
              <el-input style="width: 250px" placeholder="请填写标书的名称" v-model="bookForm.bookName" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
         <el-form-item label="标书内容" prop="content">
           <el-input
             type="textarea"
             placeholder="请标书内容"
             :rows="8"
             resize="none"
             class="textarea"  style="width: 1000px" v-model="bookForm.content">
           </el-input>
         </el-form-item>
        </el-row>
      </el-form>
      <div>
        <p>附件上传</p>
        <div>
          <el-upload
            class="upload-demo"
            ref="upload"
            action="123"
            :multiple="false"
            :file-list="fileList"
            :before-upload="beforeUpload"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "AddTenderingFile",
    data(){
      return{
        fileList:[
        ],
        projectList: '',
        bookForm:{
          projectId:'',
          projectCode:'',
          writer:'',
          amount:'',
          time:new Date(),
          content:'',
          bookName:'',
          type:'1'
        },
        rules:{
          projectId:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
          projectCode:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
          writer:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
          time:[{required: true, message: '请选择日期', trigger: 'blur'}],
          amount:[{required: true, message: '请填写金额', trigger: 'blur'}],
          content:[{required: true, message: '请填写标书内容', trigger: 'blur'}],
          bookName:[{required: true, message: '请填写标书名称', trigger: 'blur'}],
        }
      }
    },
    mounted() {
      this.initProject();
    },
    methods: {
      initProject(){
        this.getRequest('/tendering/getAllApprovedProject?page=' + 1 + '&size=' + 1000).then(resp => {
          if (resp) {
            this.projectList = resp.data;
          }
        });
      },
      beforeUpload(file){
        let fm = new FormData();
        fm.append('file',file);
        this.postRequest("/file/upload?projectId="+this.bookForm.projectId+'&type='+this.bookForm.type,fm).then(resp=>{
        })
        return false;
      },
      submitUpload() {
        this.$refs.bookForm.validate((valid)=>{
          if(valid){
            this.postKeyValueRequest('/file/saveFile',this.bookForm).then(resp=>{
              if(resp){
                this.$refs.upload.submit();

              }
            })
          }else{
            this.$message.error("请输入所有字段！")
            return  false
          }
        })

      },

      changeProject(projectid){
       this.projectList.forEach(item=>{
         if(item.urid === projectid){
           this.bookForm.projectCode = item.code;
           this.bookForm.writer = item.createdby;
         }
       })

      }
    }
  }
</script>

<style scoped>

</style>
