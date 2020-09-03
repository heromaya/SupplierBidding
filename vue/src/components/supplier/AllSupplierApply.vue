<template>
  <div>
    <div v-if="showInfo">
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
            <el-button type="primary" @click="refresh">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-table
          :data="SupplierData"
          border
          stripe
          size="small"
          :header-cell-style="headClass"
          style="width: 100%;"
        >
          <el-table-column
            align="center"
            label="序号"
            type="index"
            width="50">
          </el-table-column>
          <el-table-column
            prop="userid"
            align="center"
            label="账号"
          >
          </el-table-column>
          <el-table-column
            v-if="1==2"
            prop="processInstenceid"
            align="center"
            label="流程id">
          </el-table-column>
          <el-table-column
            prop="name"
            align="center"
            label="名称"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            align="center"
            label="地址"
          >
          </el-table-column>
          <el-table-column
            prop="typeid"
            align="center"
            label="类型">
          </el-table-column>
          <el-table-column
            prop="taxid"
            align="center"
            label="统一社会信用代码">
          </el-table-column>
          <el-table-column
            prop="email"
            align="center"
            width="200px"
            label="邮箱">
          </el-table-column>
          <el-table-column
            prop="telephone"
            align="center"
            label="联系方式">
          </el-table-column>
          <el-table-column
            prop="updatedon"
            align="center"
            label="申请时间">
          </el-table-column>
          <el-table-column
            prop="approvestate"
            align="center"
            label="审批状态">
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.approvestate == 1"  effect="dark">未审批</el-tag>
              <el-tag type="info" v-if="scope.row.approvestate == 2"  effect="dark">审批中</el-tag>
              <el-tag type="success" v-if="scope.row.approvestate == 3"  effect="dark">已审批</el-tag>
              <el-tag type="danger" v-if="scope.row.approvestate == 4"  effect="dark">已拒绝</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="cancelstate"
            align="center"
            label="作废状态">
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.cancelstate == 1"  effect="dark">未作废</el-tag>
              <el-tag type="" v-if="scope.row.cancelstate == 2"  effect="dark">作废中</el-tag>
              <el-tag type="danger" v-if="scope.row.cancelstate == 3"  effect="dark">已作废</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                size="small"  type="primary" @click="deatil(scope.row)"
              >查看详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="display: flex;justify-content: flex-end;margin-top: 20px">
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
      <div style="margin-top: 20px;" v-if="supplierDetailButton">
        <el-button type="primary" size="small" plain @click="backToFirst">返回</el-button>
        <el-button type="primary" size="small" plain @click="searchPicture">查看流程图</el-button>
      </div>
      <div v-if="supplierDetail" style="margin-top: 30px ;">
        <el-form :model="SupplierInfo">
          <hr>
          <el-row>
            <el-col :span="8">
              <el-form-item label="账号" label-width="70px">
                <el-input v-model="SupplierInfo.userid" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="名称" label-width="70px">
                <el-input v-model="SupplierInfo.name" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="地址" label-width="120px">
                <el-input v-model="SupplierInfo.address" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="申请时间">
                <el-input v-model="SupplierInfo.createdon" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="邮箱" label-width="70px">
                <el-input v-model="SupplierInfo.email" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="联系方式" style="margin-left: 55px;">
                <el-input v-model="SupplierInfo.telephone" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="审批状态">
                <el-input v-model="SupplierInfo.approvestate" disabled class="itemDetail" size="small">

                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="作废状态">
                <el-input v-model="SupplierInfo.cancelstate" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="统一社会信用代码">
                <el-input v-model="SupplierInfo.taxid" disabled class="itemDetail" size="small"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
        <hr>
      </div>
      <div v-if="showPicture">
        <div >
          <el-button type="primary" size="small"  @click="backToDetail">返回</el-button>
          <el-button type="primary" size="small"  @click="agree" :disabled="canApproved">同意</el-button>
          <el-button type="primary" size="small"  @click="refused" :disabled="canApproved">拒绝</el-button>
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
    name: "AllSupplierApply",
    data() {
      return {
        showInfo: true,
        showDetail: false,
        supplierDetail: true,
        showPicture: false,
        supplierDetailButton: true,
        showPictureButton: false,
        canApproved:true,
        form: {
          approveState: '1',
          cancelState: '1',
        },
        SupplierData: [],
        SupplierInfo: {
          userid: '',
          processInstenceid: '',
          name: '',
          address: '',
          typeid: '',
          taxid: '',
          email: '',
          telephone: '',
          approvestate: '',
          cancelstate: '',
          createdon: ''
        },
        total: 0,
        page: 1,
        size: 10,
        src: ''
      }
    },
    mounted() {
      this.initSupplier()
    },
    methods: {
      headClass(){
        return 'background:lightblue;letter-spacing:3px;font-weight:bolder;font-size:14px;font-family:楷体'
      },
      deatil(data) {
        this.showInfo = false;
        this.showDetail = true
        Object.assign(this.SupplierInfo, data);
        switch (this.SupplierInfo.approvestate) {
          case "1":
            this.SupplierInfo.approvestate = '未审批'
            break
          case "2":
            this.SupplierInfo.approvestate = '审批中'
            break
          case "3":
            this.SupplierInfo.approvestate = '已审批'
            break
          case "4":
            this.SupplierInfo.approvestate = '已拒绝'
            break
        }
        switch (this.SupplierInfo.cancelstate) {
          case "1":
            this.SupplierInfo.cancelstate = '未作废'
            break
          case "2":
            this.SupplierInfo.cancelstate = '作废中'
            break
          case "3":
            this.SupplierInfo.cancelstate = '已作废'
            break
        }

      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initSupplier();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initSupplier();
      },
      initSupplier() {
        this.getRequest("/supplier/getAllSupplier" + "?page=" + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.SupplierData = resp.data;
            this.total = resp.total;
          }
        })
      },
      query() {
        this.getRequest("/supplier/queryByApproveAndCancel?approveState=" + this.form.approveState + "&cancelState=" + this.form.cancelState).then(resp => {
          if (resp) {
            this.SupplierData = resp.data;
            this.total = resp.total
          }
        })
      },
      firstbtn() {
        this.showInfo = true;
        this.showDetail = false;
        this.supplierDetail = true;
        this.showPicture = false;
        this.supplierDetailButton = true;
        this.showPictureButton = false;
      },
      secondbtn() {
        this.supplierDetail = true;
        this.showPicture = false;
        this.supplierDetailButton = true;
        this.showPictureButton = false;
      },
      backToFirst() {
        this.firstbtn();
        this.initSupplier();
      },
      backToDetail() {
        this.secondbtn();
        this.initSupplier();
      },
      agree() {
        this.getRequest('/supplier/approve?processInstanceId=' + this.SupplierInfo.processInstenceid + "&userid=" + this.SupplierInfo.userid + "&approvestate=" + "3").then(resp => {
          if (resp) {
            this.backToFirst();
          }
        })
      },
      refused() {
        this.getRequest('/supplier/approve?processInstanceId=' + this.SupplierInfo.processInstenceid + "&userid=" + this.SupplierInfo.userid + "&approvestate=" + "4").then(resp => {
          if (resp) {
            this.backToFirst();
          }
        })
      },
      searchPicture() {
        this.supplierDetail = false;
        this.showPicture = true;
        this.supplierDetailButton = false;
        this.showPictureButton = true;
        if(this.SupplierInfo.approvestate == '未审批' || this.SupplierInfo.approvestate == '审批中'){
          this.canApproved = false;
        }
        this.src = 'http://localhost:8888/supplier/getImage?processInstanceId=' + this.SupplierInfo.processInstenceid
      },
      refresh() {
        this.form.approveState = '1';
        this.form.cancelState = '1';
        this.backToFirst();
        this.initSupplier();
      }
    }

  }
</script>

<style scoped>
  .itemDetail {
    width: 200px;
  }
</style>


