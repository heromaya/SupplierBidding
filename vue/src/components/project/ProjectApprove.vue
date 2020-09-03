<template>
  <div>
    <div v-if="showList">
      <div>
        <el-form :inline="true" :model="form" class="demo-form-inline">
          <el-form-item label="审批状态">
            <el-select v-model="form.approveState" placeholder="审批状态">
              <el-option label="未审批" value="1"></el-option>
              <el-option label="审批中" value="2"></el-option>
              <el-option label="已审批" value="3"></el-option>
              <el-option label="已拒绝" value="4"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="作废状态">
            <el-select v-model="form.cancelState" placeholder="作废状态">
              <el-option label="未作废" value="1"></el-option>
              <el-option label="作废中" value="2"></el-option>
              <el-option label="已作废" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="query">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-table
          size="mini"
          ref="multipleTable"
          :data="projectForm"
          tooltip-effect="dark"
          border
          stripe
          :header-cell-style="headClass"
          style="width: 100%;margin-top: 30px"
        >
          <el-table-column
            type="index"
            align="center"
            label="序号"
            :disabled="isEdit"
          >
          </el-table-column>
          <el-table-column
            prop="projectname"
            align="center"
            label="项目名称"
          >
          </el-table-column>
          <el-table-column
            prop="projectcode"
            align="center"
            label="项目编号"
          >
          </el-table-column>
          <el-table-column
            prop="tenderingid"
            align="center"
            label="编制人"
          >
          </el-table-column>
          <el-table-column
            prop="invitationtype"
            align="center"
            label="招标方式"
          >
          </el-table-column>
          <el-table-column
            prop="projectcontent"
            align="center"
            label="项目内容"
          >
          </el-table-column>
          <el-table-column
            prop="approvestate"
            align="center"
            label="审核状态"
          >
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.approvestate == 1" effect="dark">未审批</el-tag>
              <el-tag type="" v-if="scope.row.approvestate == 2" effect="dark">审批中</el-tag>
              <el-tag type="success" v-if="scope.row.approvestate == 3" effect="dark">已审批</el-tag>
              <el-tag type="danger" v-if="scope.row.approvestate == 4" effect="dark">已拒绝</el-tag>
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
            align="center"
            label="项目类型"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="createdon"
            label="创建时间"
            width="200px"
          >
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="260px"
          >
            <template slot-scope="scope">
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
        <el-button type="primary" size="small" @click="queryImage" v-if="showGetImgBtn">查看流程图</el-button>
      </div>
      <div v-if="showMain">
        <div style="width: 100% ;">
          <el-form :inline="true" :model="registerForm">
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
                <el-form-item label="项目名称"  class="label" prop="projectname">
                  <el-input v-model="registerForm.projectname" placeholder="项目名称" class="inputInfo"
                            :disabled="isEdit" style="width: 250px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="编制人"  class="label" prop="code">
                  <el-input v-model="registerForm.tenderingid" placeholder="编制人" class="inputInfo"
                            :disabled="isEdit" style="width: 250px"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目编号" class="firstLabel" prop="projectcode">
                  <el-input v-model="registerForm.projectcode" placeholder="项目编号" class="inputInfo"
                            style="width: 250px" :disabled="isEdit"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目类型"  class="label" prop="typeid">
                  <el-input v-model="registerForm.typeid" placeholder="项目类型" class="inputInfo"
                            :disabled="isEdit" style="width: 250px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招标方式"  class="label" prop="invitationtype">
                  <el-input v-model="registerForm.invitationtype" placeholder="招标方式" class="inputInfo"
                            :disabled="isEdit" style="width: 250px"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="项目内容" class="firstLabel" prop="projectcontent">
                  <el-input
                    type="textarea"
                    placeholder="请输入内容"
                    v-model="registerForm.projectcontent"
                    :rows="4"
                    resize="none"
                    class="textarea" :disabled="isEdit" style="width: 800px">
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div
          style="display: flex;border-top: 1px solid lightblue;height: 40px;line-height: 40px;border-bottom: 1px solid lightblue">
          <span style="margin-left: 20px;">采购明细</span>
          <el-button plain type="primary" size="mini" style="height: 30px;margin-top: 5px;margin-left: 20px"
                     :disabled="isEdit">增行
          </el-button>
          <el-button plain type="primary" size="mini" style="height: 30px;margin-top: 5px;margin-left: 20px"
                     :disabled="isEdit">删行
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
            >
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
          <el-button size="small" type="primary" @click="backToDetail">返回</el-button>
          <el-button size="small" type="primary" @click="doAgree" :disabled="canApprove">通过</el-button>
          <el-button size="small" type="primary" @click="doRefused" :disabled="canApprove">拒绝</el-button>
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
    name: "ProjectApprove",
    data() {
      return {
        form: {
          approveState: '1',
          cancelState: '1',
        },
        projectForm: [],
        total: 0,
        page: 1,
        size: 10,
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
        showDetail: false,
        showList: true,
        showMain: false,
        showBtnGroup: false,
        showImage: false,
        src: '',
        canApprove: false,
        showGetImgBtn:true,
      }
    },
    mounted() {
      this.initProject()
    },
    methods: {
      headClass() {
        return 'background:lightblue;letter-spacing:3px;font-weight:bolder;font-size:18px;font-family:楷体'
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initProject();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initProject();
      },
      initProject() {
        this.getRequest('/project/getAllProject?page=' + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.projectForm = resp.data;
            this.total = resp.total;
          }
        })
      },
      query() {
        this.getRequest('project/findProject?approvestate='+this.form.approveState+'&cancelstate='+this.form.cancelState).then(resp=>{
          if(resp){
            this.projectForm = resp.data;
            this.total = resp.total;
          }
        })
      },
      queryDetail(data) {
        Object.assign(this.registerForm, data)
        this.showList = false;
        this.showDetail = true;
        this.showBtnGroup = true;
        this.showMain = true;
        if(this.registerForm.approvestate =='1'){
          this.showGetImgBtn = false;
        }
      },
      back() {
        this.showList = true;
        this.showDetail = false;
      },
      queryImage() {
        if(this.registerForm.approvestate=='3'|| this.registerForm.approvestate =='4'){
          this.canApprove = true;
        }
        this.showBtnGroup = false;
        this.showMain = false;
        this.showImage = true;
        this.src = 'http://localhost:8888/supplier/getImage?processInstanceId=' + this.registerForm.processInstenceid
      },
      backToDetail() {
        this.showImage = false;
        this.showDetail = true;
        this.showBtnGroup = true;
        this.showMain = true;
      },
      doAgree() {
        this.getRequest('/project/approve?projectcode=' + this.registerForm.projectcode + '&processInstanceId=' + this.registerForm.processInstenceid + '&approvestate=3').then(resp => {
          if (resp) {
            this.initProject();
            this.showDetail=false;
            this.showList=true;
            this.showImage =false;
            this.back();
          }
        })
      },
      doRefused() {
        this.getRequest('/project/approve?projectcode=' + this.registerForm.projectcode + '&processInstanceId=' + this.registerForm.processInstenceid + '&approvestate=4').then(resp => {
          if (resp) {
            this.initProject();
            this.showDetail=false;
            this.showList=true;
            this.showImage =false;
            this.back();
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
