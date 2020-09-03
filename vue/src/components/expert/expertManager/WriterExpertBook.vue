<template>
  <div>
    <div>
      <el-button type="primary" size="small" @click="submit">提交</el-button>
    </div>
    <div style="margin-top: 10px;border: 1px solid lightblue;padding: 10px 10px">
      <el-form :model="commontbook" :rules="rules" ref="commontbook">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目名称" prop="projectid">
              <el-select style="width: 220px" v-model="commontbook.projectid" @change="changeProject">
                <el-option v-for="(project,indexj) in projectList"
                           :label="project.projectname"
                           :value="project.urid"
                           :key="indexj"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商" prop="supplierid">
              <el-select style="width: 220px" v-model="commontbook.supplierid">
                <el-option v-for="(supplier,indexj) in supplierList"
                           :label="supplier.userid"
                           :value="supplier.urid"
                           :key="indexj"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="评分" prop="score">
              <el-input placeholder="评分" type="number" v-model="commontbook.score" style="width: 250px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="评语" prop="content">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              :rows="12"
              resize="none"
              class="textarea" style="width: 1000px" v-model="commontbook.content">
            </el-input>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "WriterExpertBook",
    data() {
      return {
        projectList: [],
        supplierList: [],
        commontbook: {
          projectid: '',
          supplierid: '',
          score: '',
          content: '',
        },
        rules: {
          projectid: [{required: true, message: '请选择项目名称', trigger: 'blur'}],
          supplierid: [{required: true, message: '请选择供应商', trigger: 'blur'}],
          score: [{required: true, message: '请填写评分', trigger: 'blur'}],
          content: [{required: true, message: '请填写评语', trigger: 'blur'}],
        }
      }
    },
    mounted() {
      this.initProjectList()
    },
    methods: {
      initProjectList() {
        this.getRequest('/expert/getProject').then(resp => {
          if (resp) {
            this.projectList = resp;
          }
        })
      },
      submit() {
        this.$refs.commontbook.validate((valid) => {
          if (valid) {
            this.postRequest('/expert/saveCommontBook',this.commontbook).then(resp=>{
              //保存成功！
            })
          } else {
            this.$message.error("请输入所有字段！")
            return false
          }
        })
      },
      changeProject(projectid) {
        this.projectList.forEach(item => {
          if (item.urid == projectid) {
            this.supplierList = item.supplierList;
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
