<template>
  <div>
    <div>
      <h1 style="text-align: center">招标文件购买申请</h1>
      <el-button type="primary" size="small" @click="save">提交申请</el-button>
    </div>
    <div style="border: 1px solid lightblue;padding: 10px 10px;margin-top: 10px">
      <el-form :model="bookForm" :rules="rules" ref="bookForm">
        <el-row>
          <el-col :span="8">
            <el-form-item label="招标编号" disabled="true" prop="code">
              <el-input style="width: 220px" :disabled="true" v-model="bookForm.code" placeholder="请选择项目"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目名称" prop="projectid">
              <el-select style="width: 220px" v-model="bookForm.projectid" @change="changeProject">
                <el-option v-for="(project,indexj) in projectList"
                :label="project.projectname"
                :value="project.urid"
                :key="indexj"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="招标人" prop="tenderid">
              <el-input style="width: 220px" disabled="true" :disabled="true" v-model="bookForm.tenderid"  placeholder="请选择项目"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="文件费用" prop="amount">
              <el-input style="width: 220px" :disabled="true" v-model="bookForm.amount"  placeholder="请选择项目"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="content">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              :rows="4"
              resize="none"
              class="textarea"  style="width: 800px" v-model="bookForm.content">
            </el-input>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "BuyTenderingBook",
    data(){
      return{
        bookForm: {
          code:'',
          projectid:'',
          tenderid:'',
          amount:'',
          content:'',
        },
        projectList:'',
        rules:{
          code:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
          projectid:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
          tenderid:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
          amount:[{required: true, message: '请选择项目名', trigger: 'blur'}],
          content:[{required: true, message: '请填写备注信息', trigger: 'blur'}],
        }
      }
    },
    mounted(){
      this.initTenderBook();
    },
    methods:{
      initTenderBook(){
        this.getRequest('/bid/getAllProjectTenderBook').then(resp=>{
          if(resp){
           this.projectList = resp
          }
        })
      },
      changeProject(projectid){
        this.projectList.forEach(item=>{
          if(item.urid === projectid){
            this.bookForm.code = item.code;
            this.bookForm.tenderid = item.tenderingid;
            this.bookForm.amount = item.userfile.amount;
          }
        })
      },
      save(){
        this.$refs.bookForm.validate((valid)=>{
         if(valid){
           this.postKeyValueRequest('/bid/applyBuyTenderBook',this.bookForm).then(resp=>{
             if(resp){
               //申请成功；
             }
           })
         }else {
           this.$message.error("请输入所有字段！")
           return  false
         }
        })
      }
    }
  }
</script>

<style scoped>

</style>
