<template>
  <div>
    <div v-if="showList">
      <div>
        <el-input prefix-icon="el-icon-search" size="small" style="width:200px" placeholder="请输入项目编号查询"></el-input>
        <el-button @click="query" size="small" icon="el-icon-search" type="primary" style="margin-left: 10px">查询
        </el-button>
        <el-button size="small" type="primary" icon="el-icon-refresh" @click="refresh">刷新</el-button>
      </div>
      <div>
        <el-table
          ref="multipleTable"
          :data="projectData"
          tooltip-effect="dark"
          border
          stripe
          size="mini"
          :header-cell-style="headClass"
          style="width: 100%;margin-top: 30px"
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            type="selection"
          >
          </el-table-column>
          <el-table-column
            prop="projectname"
            label="项目名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="projectcode"
            label="项目编号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="tenderingid"
            label="编制人"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="invitationtype"
            label="招标方式"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="projectcontent"
            label="项目内容"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="approvestate"
            label="审核状态"
            align="center"
          >
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.approvestate == 1" effect="dark">未审批</el-tag>
              <el-tag type="" v-if="scope.row.approvestate == 2" effect="dark">审批中</el-tag>
              <el-tag type="success" v-if="scope.row.approvestate == 3" effect="dark">已审批</el-tag>
              <el-tag type="danger" v-if="scope.row.approvestate == 4" effect="dark">已拒绝
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="cancelstate"
            label="作废状态"
            align="center"
          >
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.cancelstate == 1" effect="dark">未作废</el-tag>
              <el-tag type="" v-if="scope.row.cancelstate == 2" effect="dark">作废中</el-tag>
              <el-tag type="danger" v-if="scope.row.cancelstate == 3" effect="dark">已作废</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="applydate"
            label="申请时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="typeid"
            label="项目类型"
            align="center"
          >
            <template slot-scope="scope">
              <el-select v-model="scope.row.typeid" disabled size="small">
                <el-option v-for="(projectType,indexj) in projectTypeList"
                           :key="indexj"
                           :label="projectType.name"
                           :value="projectType.urid"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            prop="createdon"
            label="创建时间"
            width="200px"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            width="260px"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="primary" size="small" @click="doApprove(scope.row)" style="margin-left: 10px">送审
              </el-button>
              <el-button type="primary" size="small" @click="doCancel(scope.row)">作废</el-button>
              <el-button size="small" type="primary" @click="queryDetail(scope.row)">查看详情</el-button>
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
    <div v-if="showDetail">
      <div v-if="showBtnGroup">
        <el-button type="primary" size="small" @click="back">返回</el-button>
        <el-button type="primary" size="small" @click="editDetail" :disabled="canEdit">修改</el-button>
        <el-button type="primary" size="small" @click="saveDetail" :disabled="showSave">保存</el-button>
        <el-button type="primary" size="small" @click="queryImage()" v-if="canQueryImage">查看流程图</el-button>
      </div>
      <div v-if="showMain">
        <div style="width: 100% ;">
          <el-form :inline="true" :model="registerForm" :rules="rules" ref="registerForm">
            <p
              style="height: 30px;padding-left: 20px;border-bottom: 1px solid lightblue;border-top:1px solid lightblue;margin-top: 20px;line-height: 30px">

              基本信息</p>
            <el-row>
              <el-col :span="8">
                <el-form-item label="申请日期" class="firstLabel" prop="applydate">
                  <el-date-picker
                    v-model="registerForm.applydate"
                    type="date"
                    :disabled="true"
                    placeholder="选择日期" class="inputInfo"
                    value-format="yyyy-mm-dd" style="width: 250px">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目名称" class="label" prop="projectname">
                  <el-input v-model="registerForm.projectname" placeholder="项目名称" class="inputInfo"
                            :disabled="isEdit" style="width: 250px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="编制人" class="label" prop="tenderingid">
                  <el-input v-model="registerForm.tenderingid" placeholder="编制人" class="inputInfo"
                            :disabled="isEdit" style="width: 250px"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目编号" class="firstLabel" prop="projectcode">
                  <el-input v-model="registerForm.projectcode" placeholder="项目编号" class="inputInfo"
                            :disabled="isEdit" style="width: 250px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目类型" class="label" prop="typeid" >
                  <el-select v-model="registerForm.typeid" placeholder="项目类型" :disabled="isEdit" style="width: 250px">
                    <el-option v-for="(projectType,indexp) in projectTypeList"
                               :key="indexp"
                               :label="projectType.name"
                               :value="projectType.urid" ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招标方式"  class="label" prop="invitationtype">
                  <el-select v-model="registerForm.invitationtype" placeholder="招标方式" :disabled="isEdit" style="width: 250px">
                    <el-option label="公开招标" value="1">公开招标</el-option>
                    <el-option label="邀标" value="2">邀标</el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="项目内容" class="firstLabel" prop="projectcontent">
              <el-input
                type="textarea"
                placeholder="请输入内容"
                v-model="registerForm.projectcontent"
                :rows="4"
                resize="none"
                class="textarea" :disabled="isEdit">
              </el-input>
            </el-form-item>
          </el-form>
        </div>
        <div
          style="display: flex;border-top: 1px solid lightblue;height: 40px;line-height: 40px;border-bottom: 1px solid lightblue">
          <span style="margin-left: 20px;">采购明细</span>
          <el-button plain type="primary" size="mini" style="height: 30px;margin-top: 5px;margin-left: 20px"
                     @click="addrows" :disabled="isEdit">增行
          </el-button>
          <el-button plain type="primary" size="mini" style="height: 30px;margin-top: 5px;margin-left: 20px"
                     @click="deleterows" :disabled="isEdit">删行
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
                  <el-input v-model="scope.row.name" class="productInput" size="mini" :disabled="isEdit"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="type"
                label="规格型号"
                class="productTD">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.type" class="productInput" size="mini" :disabled="isEdit"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="unit"
                label="计量单位"
                class="productTD">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.unit" class="productInput" size="mini" :disabled="isEdit"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="amount"
                label="计划采购数量"
                class="productTD">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.amount" class="productInput" size="mini" :disabled="isEdit"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="company"
                label="所属单位"
                class="productTD">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.company" class="productInput" size="mini" :disabled="isEdit"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="time"
                label="采购时间"
                class="productTD">
                <template slot-scope="scope">
                  <el-date-picker v-model="scope.row.time" class="productInput" size="mini" :disabled="isEdit"
                                  value-format="yyyy-MM-dd"></el-date-picker>
                </template>
              </el-table-column>
              <el-table-column
                prop="address"
                label="采购地点"
                class="productTD">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.address" class="productInput" size="mini" :disabled="isEdit"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="supplierid"
                label="候选供应商名单"
                class="productTD">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.supplierid" class="productInput" size="mini"
                            :disabled="isEdit"></el-input>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </div>
      </div>
      <div v-if="showImage">
        <div>
          <el-button size="small" type="primary" @click="backToSecond">返回</el-button>
        </div>
        <div>
          <img :src="src">
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    name: "TenderingProject",
    data() {
      return {
        canEdit:false,
        canQueryImage:false,
        projectTypeList: '',
        projectData: [],
        multipleSelection: [],
        total: 0,
        page: 1,
        size: 10,
        showList: true,
        showDetail: false,
        showBtnGroup: false,
        showMain: false,
        showImage: false,
        registerForm: {
          processInstenceid: '',
          approvestate: '',
          applydate: '',
          projectname: '',
          tenderingid: '',
          projectcontent: '',
          typeid: '',
          projectcode: '',
          invitationtype: '',
          productList: [],
        },
        isEdit: true,
        showSave: true,
        src: '',
        rules: {
          projectname: [{required: true, message: '请输入项目名称', trigger: 'blur'}],
          tenderingid: [{required: true, message: '请输入编制人', trigger: 'blur'}],
          applydate: [{required: true, message: '请选择申请日期', trigger: 'blur'}],
          projectcontent: [{required: true, message: '请输入项目内容', trigger: 'blur'}],
          typeid: [{required: true, message: '请选择项目类型', trigger: 'blur'}],
          projectcode: [{required: true, message: '请输入项目编号', trigger: 'blur'}],
          invitationtype: [{required: true, message: '请选择招标方式', trigger: 'blur'}],
          tenderingid: [{required: true, message: '请输入编制人', trigger: 'blur'}]
        }
      }
    },
    mounted() {
      this.initProject();
    },
    methods: {
      refresh(){
        this.initProject();
      },
      headClass() {
        return 'background:lightblue;letter-spacing:3px;font-weight:bolder;font-size:14px;font-family:楷体'
      },
      initProject() {
        this.getRequest('/tendering/getAllProjectByCode?page=' + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.projectData = resp.data;
            this.total = resp.total;
          }
        })
        this.getRequest('/dictionary/getAllProjectType').then(resp => {
          if (resp) {
            this.projectTypeList = resp;
          }
        })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initProject();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initProject();
      },
      doApprove(data) {
        if (data.approvestate != '1') {
          this.$message.error("项目不是未审核状态,不允许重复送审！");
          return false;
        }
        this.getRequest('/tendering/approveProject?projectcode=' + data.projectcode).then(resp => {
          if (resp) {
            this.initProject();
          }
        })
      },
      doCancel(data) {
        if (data.approvestate == '2') {
          this.$message.error("项目正在审核中,不允许作废！");
          return false;
        }
      },
      query() {

      },
      queryDetail(data) {
        this.showList = false;
        this.showDetail = true;
        this.showBtnGroup = true;
        this.showMain = true;
        Object.assign(this.registerForm, data);
        if(data.approvestate == 1){
          this.canEdit = true;
          this.canQueryImage = false;
        }else{
          this.canEdit = false;
          this.canQueryImage = true;
        }
      },
      /***
       * 详情
       */
      back() {
        this.showList = true;
        this.showDetail = false;
      },
      editDetail() {
        if (this.registerForm.approvestate == '2') {
          this.$message.error("项目正在审批中不允许修改！")
          return false;
        }
        this.isEdit = false;
        this.showSave = false;
        this.registerForm.applydate = new Date().toDateString();

      },
      saveDetail() {
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
        //更新数据
      },
      queryImage(data) {
        this.showBtnGroup = false;
        this.showMain = false;
        this.showImage = true;
        this.src = 'http://localhost:8888/supplier/getImage?processInstanceId=' + this.registerForm.processInstenceid
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
      backToSecond() {
        this.showImage = false;
        this.showBtnGroup = true;
        this.showMain = true;
        this.isEdit = true;
      }
    }
  }
</script>

<style scoped>

</style>
