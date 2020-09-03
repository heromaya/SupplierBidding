<template>
    <div>
      <div>
        <h1 style="text-align: center">投标文件编制</h1>
        <el-button type="primary" size="small" @click="submit">提交</el-button>
      </div>
      <div style="padding: 10px 10px; margin-top: 10px; border: 1px solid lightblue">
        <el-form :model="bookForm" :rules="rules" ref="bookForm">
          <el-row>
            <el-col :span="8">
              <el-form-item label="项目名称" prop="projectId">
                <el-select style="width: 250px" v-model="bookForm.projectId" @change="changeProject">
                  <el-option v-for="(project,indexj) in projectList"
                             :label="project.projectname"
                             :value="project.urid"
                             :key="indexj"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="招标商" prop="tenderingid">
                <el-input style="width: 250px" v-model="bookForm.tenderingid" :disabled="true" placeholder="请选择项目"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="招标编号" prop="code">
                <el-input style="width: 250px" v-model="bookForm.code" :disabled="true" placeholder="请选择项目"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="文件标题" prop="bookName">
                <el-input style="width: 250px" v-model="bookForm.bookName" placeholder="请填写文件标题"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="标书内容" prop="content">
              <el-input
                type="textarea"
                placeholder="请输入内容"
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
        name: "MakeBidBook",
      data(){
          return{
            fileList:[],
            bookForm:{
              projectId:'',
              tenderingid:'',
              code:'',
              bookName:'',
              content:'',
              amount:'0',
              type:'2',
            },
            projectList:'',
            rules:{
              projectId:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
              tenderingid:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
              code:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
              bookName:[{required: true, message: '请填写文件名称', trigger: 'blur'}],
              content:[{required: true, message: '请选择文件内容 ', trigger: 'blur'}],
            }
          }
      },
      mounted(){
        this.initProject()
      },
      methods:{
        initProject(){
          //获取已购买招标文件的项目
          this.getRequest('/bid/getAllBuyProject').then(resp=>{
            if(resp){
              this.projectList = resp;
            }
          })
        },
        changeProject(projectid){
          this.projectList.forEach(item=>{
            if(item.urid == projectid){
              this.bookForm.code = item.code;
              this.bookForm.tenderingid = item.tenderingid;
            }
          })
        },
        beforeUpload(file){
          let fm = new FormData();
          fm.append('file',file);
          this.postRequest("/file/upload?projectId="+this.bookForm.projectId+'&type='+this.bookForm.type,fm).then(resp=>{
          })
          return false;
        },
        submit(){
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
        }
      },
    }
</script>

<style scoped>

</style>
