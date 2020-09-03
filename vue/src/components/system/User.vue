<template>
  <div>
    <div>
      <div>
        <el-button size="small" type="primary" @click="showAdd">新增</el-button>
        <el-button size="small" type="primary" @click="doActive" :disabled="multipleSelection.length==0">生效</el-button>
        <el-button size="small" type="primary" @click="doDisActive" :disabled="multipleSelection.length==0">失效</el-button>
        <el-button type="primary" size="small" @click="highSearch">
          <i class="fa fa-angle-double-down" aria-hidden="true" style="margin-right: 5px" ></i>高级搜索
        </el-button>
        <el-input size="small" placeholder="请输入用户账号" icon="el-icon-search" style="width: 300px;margin-left: 600px" :disabled="search"></el-input>
        <el-button size="small" icon="el-icon-search" type="primary" style="margin-left: 10px" @click="search" :disabled="search">搜索</el-button>
      </div>
      <div
        style="border: 1px solid #409EFF;border-radius: 5px;box-sizing: border-box;padding: 5px 5px;margin: 10px 0px" v-if="showHighSerach">
        <el-row>
          <el-col :span="5">
            角色名称:
            <el-select v-model="searchSelected" placeholder="请选择" style="margin-left: 5px" size="small">
              <el-option
                v-for="(r,indexk) in searchAllRole"
                :key="indexk"
                :label="r.name"
                :value="r.urid">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-button @click="searchByRole" type="primary" size="small">查询</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
    <div>
      <el-table
        :data="UserData"
        border
        stripe
        @selection-change="handleSelectionChange"
        style="width: 100%;margin-top: 20px"
      >
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          type="index"
          width="50">
        </el-table-column>
        <el-table-column
          prop="code"
          label="账号"
        >
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status==1" style="margin-left: 30px" type="info" effect="dark">未激活</el-tag>
            <el-tag v-if="scope.row.status==2" style="margin-left: 30px" type="success" effect="dark">已激活</el-tag>
            <el-tag v-if="scope.row.status==3" style="margin-left: 30px" type="danger" effect="dark">已禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="lastlogintime"
          label="上次登录时间"
        >
        </el-table-column>
        <el-table-column
          prop="createdby"
          label="创建人">
        </el-table-column>
        <el-table-column
          prop="createdon"
          label="创建时间">
        </el-table-column>
        <el-table-column
          prop="roleList"
          label="角色">
          <template slot-scope="scope">
            <el-tag v-for="(role,index) in scope.row.roleList" :key="index" style="margin-left: 5px" effect="dark"
                    type="success">{{role.name}}
            </el-tag>
            <el-popover
              placement="right"
              title="角色列表,点击可以修改"
              width="200"
              trigger="click"
              @show="showPop(scope.row.roleList)"
            >
              <p>修改角色列表</p>
              <el-select v-model="selectdRole" placeholder="请选择" multiple>
                <el-option
                  v-for="(r,indexk) in allRoles"
                  :key="indexk"
                  :label="r.name"
                  :value="r.urid">
                </el-option>
              </el-select>
              <div style="text-align: right; margin-top: 5px">
                <el-button type="primary" size="mini" @click="hidePop(scope.row)">确定</el-button>
              </div>
              <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
            </el-popover>


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
        :total="total"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="新增用户"
      :visible.sync="isAdd"
      width="30%"
      :close-on-press-escape="false" :close-on-click-modal="false"
    >
      <div>
        <p>
          <el-tag style="width: 60px;text-align: center">用户名</el-tag>
          <el-input v-model="addUser.code" size="small" style="width: 300px;">
          </el-input>
        </p>
        <p>
          <el-tag class="updateName" style="width: 60px;text-align: center">密码</el-tag>
          <el-input v-model="addUser.password" size="small" type="password"
                    style="width: 300px"></el-input>
        </p>

      </div>
      <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="isAdd=false">取 消</el-button>
    <el-button size="small" type="primary" @click="doAdd">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name: "User",
    data() {
      return {
        showHighSerach:false,
        search:false,
        UserData: [],
        total: 0,
        page: 1,
        size: 10,
        addUser: {
          code: '',
          password: 88888888
        },
        allRoles: '',
        searchAllRole:'',
        isAdd: false,
        multipleSelection: [],
        selectdRole: [],
        searchSelected:[],
      }

    },
    mounted() {
      this.initUser();
      this.initRole();
    },
    methods: {
      hidePop(data) {
        let url = '/system/userinfo/changeRoles?userid=' + data.urid;
        this.selectdRole.forEach(role => {
          url += "&rolesid=" + role
        })
        this.putRequest(url).then(resp => {
          this.initUser();
        })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      showPop(roleList) {

        this.selectdRole = [];
        roleList.forEach(r => {
          this.selectdRole.push(r.urid)
        })
      },
      sizeChange(currentSize) {
        this.size = currentSize;
        this.initUser();
      },
      currentChange(currentPage) {
        this.page = currentPage;
        this.initUser();
      },
      initUser() {
        this.getRequest('/system/userinfo/?page=' + this.page + '&size=' + this.size).then(resp => {
          if (resp) {
            this.total = resp.total;
            this.UserData = resp.data;
          }
        })
      },
      initRole() {
        this.getRequest('/system/roleinfo/').then(resp => {
          if (resp) {
            this.allRoles = resp;
            this.searchAllRole =resp;
          }
        })
      },
      showAdd() {
        this.isAdd = true
      },
      doAdd() {
        this.postKeyValueRequest('/system/userinfo/', this.addUser).then(resp => {
          if (resp) {
            this.initUser();
            this.isAdd = false;
            this.addUser.code = ''
          }
        })
      },
      doActive() {
        let codes = '?';
        this.multipleSelection.forEach(item => {
          codes += 'codes=' + item.code + '&'
        })
        this.putRequest('/system/userinfo/2/' + codes).then(resp => {
          if (resp) {
            this.initUser();
          }
        })
      },
      doDisActive() {
        let codes = '?';
        this.multipleSelection.forEach(item => {
          codes += 'codes=' + item.code + '&'
        })
        this.putRequest('/system/userinfo/1/' + codes).then(resp => {
          if (resp) {
            this.initUser();
          }
        })
      },
      highSearch(){
        if(!this.showHighSerach){
          this.showHighSerach = true;
          this.search=true;
        }else{
          this.showHighSerach = false;
          this.search=false;
        }

      },
      searchByRole(){
        // let url = '/system/userinfo/findByrole?roleid'+ this.searchSelected[1];
        //
        // this.getRequest(url).then(resp=>{
        //   if(resp){
        //     this.total = resp.total;
        //     this.UserData = resp.data;
        //   }
        // })
      }
    }
  }
</script>

<style scoped>

</style>
