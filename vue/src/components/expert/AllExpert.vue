<template>
  <div>
    <div style="border: 1px solid lightblue ;border-radius: 5px;margin: 5px 0px;padding: 10px 5px;">
      <el-row>
        <el-col :span="8">
          类型：
          <el-select v-model="selectExpertType" size="small" placeholder="专家类型" style="width: 250px">
            <el-option
            v-for="(e,index) in expertType"
            :key="index"
            :label="e.name"
            :value="e.urid"
            ></el-option>
          </el-select>
          <el-button size="small" type="primary">查找</el-button>
        </el-col>
      </el-row>

    </div>
    <div>
      <el-table
        :data="ExpertData"
        border
        stripe
        align="center"
        :header-cell-style="headClass"
        style="width: 100%;margin-top: 20px"
      >
        <el-table-column
          prop="code"
          align="center"
          label="编号"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          align="center"
          label="姓名"
        >
        </el-table-column>
        <el-table-column
          prop="sex"
          align="center"
          label="性别">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.sex == 1" effect="dark">男</el-tag>
            <el-tag type="success" v-if="scope.row.sex == 2" effect="dark">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="education"
          align="center"
          label="学历">
        </el-table-column>
        <el-table-column
          prop="educationbackground"
          align="center"
          label="教育背景">
        </el-table-column>
        <el-table-column
          prop="specialitydomain"
          align="center"
          label="专业领域">
        </el-table-column>
        <el-table-column
          prop="typeid"
          align="center"
          label="类型">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
            >查看详情
            </el-button>
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
    name: "AllExpert",
    data() {
      return {
        expertForm: {
          typeid: '',
          edu: ''
        },
        ExpertData: [],
        total: 0,
        page: 1,
        size: 10,
        expertType:'',
        selectExpertType:''
      }
    },
    mounted() {
      this.initExpert();
    },
    methods: {
      headClass() {
        return 'background:lightblue;letter-spacing:3px;font-weight:bolder;font-size:18px;font-family:楷体'
      },
      initExpert() {
        this.getRequest('/expert/getAllExpert?page=' + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.ExpertData = resp.data;
            this.total = resp.total;
          }
        })
        this.getRequest('/dictionary/getAllExpertType').then(resp=>{
          if(resp){
            this.expertType = resp;
          }
        })
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initExpert();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initExpert();
      },
      query() {

      }

    }
  }
</script>

<style scoped>

</style>
