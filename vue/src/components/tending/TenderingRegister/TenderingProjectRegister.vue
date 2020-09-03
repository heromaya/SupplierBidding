<template>
  <div class="box">
    <div class="top">
      <el-button size="small" type="primary" class="btn" plain @click="save">保存</el-button>
      <p class="title">招标计划申请登记</p>
    </div>
    <div class="main">
      <div style="width: 100% ;">
        <el-form :inline="true" :model="registerForm" :rules="rules" ref="registerForm">
          <p
            style="height: 30px;padding-left: 20px;border-bottom: 1px solid lightblue;margin-top: 0px;line-height: 30px">

            基本信息</p>
          <el-form-item label="申请日期" class="firstLabel" prop="applydate">
            <el-date-picker
              v-model="registerForm.applydate"
              type="date"
              :disabled="true"
              placeholder="选择日期" class="inputInfo"
              value-format="yyyy-mm-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="项目名称"  class="label" prop="projectname">
            <el-input v-model="registerForm.projectname" placeholder="项目名称" class="inputInfo"></el-input>
          </el-form-item>
          <el-form-item label="编制人"  class="label" prop="tenderingid">
            <el-input v-model="registerForm.tenderingid" placeholder="编制人" class="inputInfo"></el-input>
          </el-form-item>
          <el-form-item label="项目编号" class="firstLabel" prop="projectcode">
            <el-input v-model="registerForm.projectcode" placeholder="项目编号" class="inputInfo"
                      ></el-input>
          </el-form-item>
          <el-form-item label="项目类型"  class="label" prop="typeid">
            <el-select v-model="registerForm.typeid"  placeholder="项目类型" >
              <el-option v-for="(projectType,indexp) in projectList"
                         :key="indexp"
                         :label="projectType.name"
                         :value="projectType.urid"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="招标方式"  class="label" prop="invitationtype">
            <el-select v-model="registerForm.invitationtype" placeholder="招标方式">
              <el-option label="公开招标"  value="1">公开招标</el-option>
              <el-option label="邀标"  value="2">邀标</el-option>
            </el-select>

          </el-form-item>
          <el-form-item label="项目内容" class="firstLabel" prop="projectcontent">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              v-model="registerForm.projectcontent"
              :rows="4"
              resize="none"
              class="textarea">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div
        style="display: flex;border-top: 1px solid lightblue;height: 40px;line-height: 40px;border-bottom: 1px solid lightblue">
        <span style="margin-left: 20px;">采购明细</span>
        <el-button plain type="primary" size="mini" style="height: 30px;margin-top: 5px;margin-left: 20px"
                   @click="addrows">增行
        </el-button>
        <el-button plain type="primary" size="mini" style="height: 30px;margin-top: 5px;margin-left: 20px"
                   @click="deleterows">删行
        </el-button>
      </div>
      <div class="tableInfo">
        <template>
          <el-table
            ref="multipleTable"
            size="mini"
            border
            style="width: 100%"
            :data="registerForm.productList"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              prop="name"
              label="物品名称"
              class="productTD">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" class="productInput" size="mini"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="type"
              label="规格型号"
              class="productTD">
              <template slot-scope="scope">
                <el-input v-model="scope.row.type" class="productInput" size="mini"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="unit"
              label="计量单位"
              class="productTD">
              <template slot-scope="scope">
                <el-input v-model="scope.row.unit" class="productInput" size="mini"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="amount"
              label="计划采购数量"
              class="productTD">
              <template slot-scope="scope">
                <el-input v-model="scope.row.amount" class="productInput" size="mini"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="company"
              label="所属单位"
              class="productTD">
              <template slot-scope="scope">
                <el-input v-model="scope.row.company" class="productInput" size="mini"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="time"
              label="采购时间"
              class="productTD">
              <template slot-scope="scope">
                <el-date-picker v-model="scope.row.time" class="productInput" size="mini"
                                value-format="yyyy-MM-dd"></el-date-picker>
              </template>
            </el-table-column>
            <el-table-column
              prop="address"
              label="采购地点"
              class="productTD">
              <template slot-scope="scope">
                <el-input v-model="scope.row.address" class="productInput" size="mini"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="supplierid"
              label="候选供应商名单"
              class="productTD">
              <template slot-scope="scope">
                <el-input v-model="scope.row.supplierid" class="productInput" size="mini"></el-input>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "TenderingProjectRegister",
    data() {
      return {
        registerForm: {
          applydate: new Date(),
          projectname: '',
          tenderingid: '',
          projectcontent: '',
          typeid: '',
          projectcode: '',
          invitationtype: '',
          productList: [],
        },
        rules: {
          projectname: [{required: true, message: '请输入项目名称', trigger: 'blur'}],
          tenderingid: [{required: true, message: '请输入编制人', trigger: 'blur'}],
          applydate: [{required: true, message: '请选择申请日期', trigger: 'blur'}],
          projectcontent: [{required: true, message: '请输入项目内容', trigger: 'blur'}],
          typeid: [{required: true, message: '请选择项目类型', trigger: 'blur'}],
          projectcode: [{required: true, message: '请输入项目编号', trigger: 'blur'}],
          invitationtype: [{required: true, message: '请选择招标方式', trigger: 'blur'}],
          tenderingid: [{required: true, message: '请填写编制人', trigger: 'blur'}]
        },
        projectList: '',
      }
    },
    mounted() {
      this.init();
    },
    methods: {
      init() {
        this.getRequest('/dictionary/getAllProjectType').then(resp => {
          if (resp) {
            this.projectList = resp;
          }
        })
      },
      addrows() {
        this.registerForm.productList.push({
          name: '',
          type: '',
          unit: '',
          amount: '0',
          company: '',
          time: new Date(),
          address: '',
          supplierid: ''
        })
      },
      deleterows() {
        this.registerForm.productList.pop();
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      save() {
        if (this.registerForm.productList.length > 0) {
          this.registerForm.productList.forEach(item => {

            if (!item.name) {
              this.$message.error("采购明细中的物品名称不能为空");
              return false;
            }
            if (!item.type) {
              this.$message.error("采购明细中的规格型号不能为空");
              return false;
            }
            if (!item.unit) {
              this.$message.error("采购明细中的计量单位不能为空");
              return false;
            }
          })
        }
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            this.postRequest('/tendering/addProject/', this.registerForm).then(resp => {
              if (resp) {

              }
            })
          } else {
            this.$message.error("请输入所有字段！")
            return false
          }
        })
      },


    }

  }
</script>

<style>
  .box {
    border: 1px solid lightblue;
  }

  .top {
    width: 100%;
    height: 100px;
  }

  .top .btn {
    margin-left: 20px;
    margin-top: 10px;
  }

  .top .title {
    text-align: center;
    font-size: 30px;
    font-family: 楷体;
    color: black;
    margin-top: 0px;
  }

  .main {
    width: 100%;
    border: 1px solid lightblue;
  }

  .firstLabel {
    margin-left: 20px;
  }


  .inputInfo {
    width: 300px;
  }

  .textarea {
    width: 1200px;
  }

  .tableInfo {
    height: 300px;
    overflow: auto;
  }

  .productInput {
    margin-top: 0px;
  }
</style>
