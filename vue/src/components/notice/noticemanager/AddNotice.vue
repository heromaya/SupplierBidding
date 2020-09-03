<template>
  <div>
    <div>
      <el-button type="primary" size="small" @click="addNotice">保存</el-button>
    </div>
    <div style="border: 1px solid lightblue;margin:10px 0px;padding: 10px 10px;">
      <el-form ref="NoticeForm" :model="NoticeForm" label-width="120px" :rules="rules">
        <el-row>
          <el-col :span="8">
            <el-form-item label="公告标题" prop="name">
              <el-input v-model="NoticeForm.name" placeholder="公告标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目" >
              <el-select v-model="selectProject" placeholder="请选择" @change="changeProject">
                <el-option
                v-for="(project,indexj) in projectData"
                :label="project.projectname"
                :value="project.urid"
                :key="indexj"
                ></el-option>
              </el-select>
            </el-form-item>
            </el-col>
          <el-col :span="8">
            <el-form-item label="招标编号" prop="code">
              <el-input v-model="NoticeForm.code" placeholder="招标编号" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="招标商" prop="tendingid">
              <el-input v-model="NoticeForm.tendingid" placeholder="招标商" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="投标截止时间" prop="time">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="NoticeForm.time"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="招标开始时间" prop="startdate">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="NoticeForm.startdate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="招标结束日期" prop="startdate">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="NoticeForm.enddate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="公告内容" prop="content">
          <el-input type="textarea" :rows="8"
                    placeholder="请输入内容"
                    v-model="NoticeForm.content"></el-input>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "AddNotice",
    data() {
      return {
        selectProject:'',
        projectData:'',
        NoticeForm: {
          name: '',
          projectid:'',
          code: '',
          tendingid: '',
          time: '',
          startdate: '',
          enddate: '',
          content: '',
        },
        rules: {
          name: [{required: true, message: '请输入公告名称', trigger: 'blur'}],
          code: [{required: true, message: '请输入招标编号', trigger: 'blur'}],
          tendingid: [{required: true, message: '请输入招标商', trigger: 'blur'}],
          time: [{required: true, message: '请选择投标截止时间', trigger: 'blur'}],
          startdate: [{required: true, message: '请选择开始日期', trigger: 'blur'}],
          enddate: [{required: true, message: '请选择结束日期', trigger: 'blur'}],
          content: [{required: true, message: '请输入公告内容', trigger: 'blur'}]
        }
      }
    },
    mounted(){
      this.init();
    },
    methods:{
      init(){
        this.getRequest('/tendering/getAllApprovedProject?size=100000').then(resp=>{
          this.projectData = resp.data;
        })
      },
      addNotice(){
        this.$refs.NoticeForm.validate((valid) => {
          if (valid) {
            this.postRequest('/notice/addNotice',this.NoticeForm).then(resp=>{
              if(resp){
                this.init();
              }
            })
          } else {
            this.$message.error("请输入所有字段！")
            return false
          }
        })
      },
      changeProject(projecturid){
        this.projectData.forEach(item=>{
          if(item.urid === projecturid){
            let project = item;
            this.NoticeForm.projectid = project.urid;
            this.NoticeForm.code = project.code;
            this.NoticeForm.tendingid = project.createdby;
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
