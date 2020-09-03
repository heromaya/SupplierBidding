<template>
  <div>

    <el-button size="small" type="primary" @click="save">保存</el-button>
   <div style="margin-top: 10px;border: 1px solid lightblue;padding: 10px 10px">
     <el-form :model="expertGroup" :rules="rules" ref="expertGroup">
       <el-row>
         <el-col :span="8">
           <el-form-item placeholder="请选择" label="招标项目" prop="projectId">
             <el-select v-model="expertGroup.projectId">
               <el-option v-for="(item,indexj) in projectList"
                          :key="indexj"
                          :label="item.projectname"
                          :value="item.urid"></el-option>
             </el-select>
           </el-form-item>
         </el-col>
         <el-col :span="8">
           <el-form-item placeholder="请选择" label="专家类别" prop="expertType">
             <el-select v-model="expertGroup.expertType">
               <el-option v-for="(expert,indexk) in expertTypeList"
                          :key="indexk"
                          :label="expert.name"
                          :value="expert.urid"></el-option>
             </el-select>
           </el-form-item>
         </el-col>
         <el-col :span="8">
           <el-form-item placeholder="请选择" label="专家个数" prop="expertCount">
             <el-select v-model="expertGroup.expertCount">
               <el-option value="3" label="3"></el-option>
               <el-option value="4" label="4"></el-option>
               <el-option value="5" label="5"></el-option>
               <el-option value="6" label="6"></el-option>
             </el-select>
           </el-form-item>
         </el-col>
       </el-row>
       <el-row>
         <el-col :span="8">
           <el-form-item label="小组名称" prop="groupName">
             <el-input v-model="expertGroup.groupName" placeholder="请输入小组名称" style="width: 220px"></el-input>
           </el-form-item>
         </el-col>
       </el-row>
     </el-form>
     <el-button size="small" type="primary" @click="build">{{btnName}}</el-button>
   </div>
    <div v-if="showExpertGroupList" style="border: 1px solid lightblue">
      <h1 style="text-align: center">专家列表</h1>
      <div>
        <el-table
          ref="singleTable"
          size="mini"
          stripe
          border
          :data="expertList"
          highlight-current-row
          style="width: 100%">
          <el-table-column
            label="序号"
            type="index"
            width="50px"
            align="center">
          </el-table-column>
          <el-table-column
            property="name"
            label="专家名称"
            align="center"
           >
          </el-table-column>
          <el-table-column
            property="sex"
            label="性别"
            align="center"
            >
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.sex == 1" effect="dark">男</el-tag>
              <el-tag type="success" v-if="scope.row.sex == 2" effect="dark">女</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            property="education"
            label="学历"
            align="center">
          </el-table-column>
          <el-table-column
            property="educationbackground"
            label="教育背景"
            align="center">
          </el-table-column>
          <el-table-column
            property="specialitydomain"
            label="专业领域"
            align="center">
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>

</template>

<script>
  export default {
    name: "BuildExpertGroup",
    data(){
      return{
        btnName:'开始抽取',
        showExpertGroupList:false,
        expertList:[],
        projectList:'',
        expertTypeList:'',
        expertGroup:{
          projectId:'',
          expertType:'',
          expertCount:'',
          groupName:'',
        },
        rules:{
          projectId:[{required: true, message: '请选择项目名称', trigger: 'blur'}],
          expertType:[{required: true, message: '请选择专家类型', trigger: 'blur'}],
          expertCount:[{required: true, message: '请选择专家数量', trigger: 'blur'}],
          groupName:[{required: true, message: '请输入小组名称', trigger: 'blur'}]
        }
      }
    },
    mounted(){
      this.initProjectList();
      this.initExpertGroup();
    },
    methods:{
      initProjectList(){
        this.getRequest('/tendering/getAllApprovedProject?page=' + 1 + '&size=' + 1000).then(resp => {
          if (resp) {
            this.projectList = resp.data;
          }
        });
      },
      initExpertGroup(){
        this.getRequest('/dictionary/getAllExpertType').then(resp=>{
          if(resp){
            this.expertTypeList = resp;
          }
        })
      },
      build(){
        this.$refs.expertGroup.validate((valid)=>{
          if(valid){
            this.showExpertGroupList = true;
            this.btnName = '重新抽取';
            this.postKeyValueRequest('/tendering/buildExpertGroup',this.expertGroup).then(resp=>{
              if(resp){
                this.expertList = resp;
              }
            })
          }else{
            this.$message.error("请输入所有字段！")
            return  false
          }
        })

      },
      save(){
        if(this.expertList.length == 0){
          this.$message.error("请先抽取专家组，再进行保存！");
          return false;
        }
        this.$confirm('此操作将保存所抽取的专家组成【' + this.expertGroup.groupName + '】，不可更改，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.postRequest('/tendering/saveExpertGroup?groupName='+this.expertGroup.groupName+'&projectId='+this.expertGroup.projectId,this.expertList).then(resp=>{
            if(resp){
              this.expertList = [];
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消保存'
          });
        });
      },
      },

  }
</script>

<style scoped>

</style>
