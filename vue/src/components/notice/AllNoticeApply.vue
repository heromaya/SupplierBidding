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
          :data="NoticeData"
          border
          stripe
          style="width: 100%;margin-top: 20px"
        >
          <el-table-column
            type="index"
            label="序号"
          ></el-table-column>
          <el-table-column
            prop="name"
            label="公告名称"
          >
          </el-table-column>
          <el-table-column
            prop="content"
            label="公告内容"
          >
          </el-table-column>
          <el-table-column
            prop="code"
            label="招标编号">
          </el-table-column>
          <el-table-column
            prop="createdby"
            label="招标负责人">
          </el-table-column>
          <el-table-column
            prop="startdate"
            label="开始时间">
          </el-table-column>
          <el-table-column
            prop="enddate"
            label="结束时间">
          </el-table-column>
          <el-table-column
            prop="time"
            label="投标时间">
          </el-table-column>
          <el-table-column
            prop="approvestate"
            label="审批状态">
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.approvestate == 1" effect="dark">未审批</el-tag>
              <el-tag type="primary" v-if="scope.row.approvestate == 2" effect="dark">审批中</el-tag>
              <el-tag type="success" v-if="scope.row.approvestate == 3" effect="dark">已审批</el-tag>
              <el-tag type="fail" v-if="scope.row.approvestate == 4" effect="dark">已拒绝</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="cancelstate"
            label="作废状态">
            <template slot-scope="scope">
              <el-tag type="info" v-if="scope.row.cancelstate == 1" effect="dark">未作废</el-tag>
              <el-tag type="info" v-if="scope.row.cancelstate == 2" effect="dark">作废中</el-tag>
              <el-tag type="success" v-if="scope.row.cancelstate == 3" effect="dark">已作废</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="queryDetail(scope.row)"
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
    <div v-if="showDetail">
      <div v-if="groupButton">
        <el-button size="small" type="primary" @click="batkToFirst">返回</el-button>
        <el-button size="small" type="primary" @click="queryImage">查看流程图</el-button>
      </div>
      <div v-if="showMain">
        <div style="border: 1px solid lightblue;padding: 5px 10px;margin: 10px 0px;">
          <el-form :inline="true" :model="NoticeObject" class="demo-form-inline" label-position="right">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公告标题">
                  <el-input v-model="NoticeObject.name" placeholder="公告标题" disabled size="small"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目">
                  <el-input v-model="NoticeObject.projectid" placeholder="项目" disabled size="small"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招标编号">
                  <el-input v-model="NoticeObject.code" placeholder="招标编号" disabled size="small"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="招标商">
                  <el-input v-model="NoticeObject.tendingid" placeholder="招标编号" disabled size="small"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标截至时间">
                  <el-input v-model="NoticeObject.time" placeholder="投标截至时间" disabled size="small"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招标开始时间">
                  <el-input v-model="NoticeObject.startdate" placeholder="招标开始时间" disabled size="small"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="招标截至时间">
                  <el-input size="small" v-model="NoticeObject.enddate" disabled></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="公告内容">
                  <el-input type="textarea" v-model="NoticeObject.content"  :rows="8"
                            resize="none" style="width: 800px" disabled></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
      <div v-if="getImage">
        <el-button @click="backToSecond" size="small" type="primary">返回</el-button>
        <el-button size="small" type="primary" @click="doApprove" v-if="canApprove">同意</el-button>
        <el-button size="small" type="primary" @click="doRefused" v-if="canApprove">拒绝</el-button>
        <div>
          <img :src="src"></div>
      </div>
      </div>

  </div>
</template>

<script>
  export default {
    name: "AllNoticeApply",
    data() {
      return {
        showList: true,
        showDetail: false,
        groupButton: false,
        showMain: false,
        getImage: false,
        canApprove:true,
        src:'',
        form: {
          approveState: '',
          cancelState: '',
        },
        NoticeData: [],
        total: 0,
        page: 1,
        size: 10,
        NoticeObject: {
          urid:'',
          code: '',
          tendingid: '',
          position: '',
          time: '',
          projectid: '',
          startdate: '',
          enddate: '',
          createdby: '',
          createdon: '',
          updatedby: '',
          updatedon: '',
          approvestate: '',
          cancelstate: '',
          content: '',
          name: '',
          processInstenceid: ''
        },
      }
    },
    mounted() {
      this.initNotice()
    },
    methods: {
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initNotice();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initNotice();
      },
      initNotice() {
        this.getRequest("/notice/getAllNotice" + "?page=" + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.NoticeData = resp.data;
            this.total = resp.total;
          }
        })
      },
      query() {
        this.getRequest("/notice/getAllNotice?approveState=" + this.form.approveState + "&cancelState=" + this.form.cancelState).then(resp => {
          if (resp) {
            this.NoticeData = resp.data;
            this.total = resp.total
          }
        })
      },
      queryDetail(data) {
        Object.assign(this.NoticeObject, data);
        this.showList = false;
        this.showDetail = true;
        this.groupButton = true;
        this.showMain = true;
        this.canApprove = true;
        this.getImage = false;
      },
      batkToFirst() {
        this.showDetail = false;
        this.showList = true;
      },
      backToSecond() {
        this.getImage = false;
        this.showMain = true;
        this.groupButton = true;
      },
      queryImage() {
        this.groupButton = false;
        this.showMain = false;
        this.getImage = true;
        if(this.NoticeObject.approvestate == '3' || this.NoticeObject.approvestate =='4'){
          this.canApprove = false;
        }
        this.src = 'http://localhost:8888/supplier/getImage?processInstanceId=' + this.NoticeObject.processInstenceid
      },
      doApprove(){
        this.getRequest("/notice/approve?urid="+this.NoticeObject.urid+"&processInstenceid="+this.NoticeObject.processInstenceid+"&approvestate=3").then(resp=>{
          if(resp){
            this.initNotice();
            this.batkToFirst();
          }
        })
      },
      doRefused(){
        this.getRequest("/notice/approve?urid="+this.NoticeObject.urid+"&processInstenceid="+this.NoticeObject.processInstenceid+"&approvestate=4").then(resp=>{
          if(resp){
            this.initNotice();
            this.batkToFirst();
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
