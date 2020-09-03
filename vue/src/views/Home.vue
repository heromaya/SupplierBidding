<template>
  <div>
    <el-container>
      <el-header class="homeHeader">
        <div class="title">招投标系统</div>
        <el-dropdown class="userInfo" @command="commanHandler">
  <span class="el-dropdown-link">
    {{user.code}}<i><img
    src="data:image/jpeg;base64,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"
    alt=""></i>
  </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
            <el-dropdown-item command="setting">设置</el-dropdown-item>
            <el-dropdown-item divided command="logout">注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container>
        <el-aside width="200px;" style="height: 895px;background-color: #293040">
          <el-menu router unique-opened background-color="#293040" style="width: 200px">
            <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden"
                        :key="index">
              <template slot="title">
                <i :class="item.icon" style="color: white"></i>
                <span style="margin-right: 5px;color: white">{{item.name}}</span>
              </template>
              <el-menu-item :index="child.path" v-for="(child,indexj) in item.children"
                            :key="indexj" style="color: white">{{child.name}}
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="homeWelcome" v-if="this.$router.currentRoute.path == '/home'">
            欢迎使用招投标管理系统!
          </div>

          <router-view class="homeRouterView"/>
        </el-main>
      </el-container>
    </el-container>
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" :show-close="false" :close-on-press-escape="false"
               :close-on-click-modal="false">
      <el-form :model="userForm" :rules="rules" ref="userForm">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="code">
          <el-input v-model="userForm.code" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="原来的密码" :label-width="formLabelWidth" prop="oldPassword">
          <el-input v-model="userForm.oldPassword" autocomplete="off" type="password" placeholder="原来的密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" :label-width="formLabelWidth" prop="newPassword">
          <el-input v-model="userForm.newPassword" autocomplete="off" type="password" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="再次输入密码" :label-width="formLabelWidth" prop="newPasswordAgain">
          <el-input v-model="userForm.newPasswordAgain" autocomplete="off" type="password"
                    placeholder="再次输入新密码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false" :disabled="canCancel">取 消</el-button>
        <el-button type="primary" @click="changePassword">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        user: JSON.parse(window.sessionStorage.getItem("user")),
        dialogFormVisible: false,
        userForm: {
          code: '',
          oldPassword: '',
          newPassword: '',
          newPasswordAgain: ''
        },
        formLabelWidth: '120px',
        canCancel: false,
        rules: {
          code: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          oldPassword: [{required: true, message: '请输入原来的密码', trigger: 'blur'}],
          newPassword: [{required: true, message: '请输入新密码', trigger: 'blur'}],
          newPasswordAgain: [{required: true, message: '请再次输入新密码', trigger: 'blur'}, {
            validator: (rule, value, callback) => {
              if (value === '') {
                callback(new Error("请再次输入新密码"))
              } else if (value !== this.userForm.newPassword) {
                callback(new Error("两次输入密码不一致！"))
              } else {
                callback()
              }
            }
          }]
        },
      }
    },
    computed: {
      routes() {
        return this.$store.state.routes;
      }
    },
    mounted() {
      this.shouldChangePassword();
    },
    methods: {

      shouldChangePassword() {
        if (this.user.changedpassword == '0') {
          this.dialogFormVisible = true;
          this.userForm.code = this.user.code;
          this.canCancel = true
        }
      },
      changePassword() {
        this.$refs.userForm.validate((valid) => {
          if (valid) {
            this.postKeyValueRequest('/changepassword', this.userForm).then(resp => {
              if (resp) {
                this.$router.replace('/')
              }
            })
          } else {
            this.$message.error("请输入所有字段！")
            return false
          }
        })
      },
      commanHandler(cmd) {
        if (cmd == 'logout') {
          this.$confirm('此操作将注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.getRequest('/logout');
            window.sessionStorage.removeItem("user");
            this.$store.commit('initRoutes', []);
            this.$router.replace('/');
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消操作'
            });
          });
        }
      },
    }
  }
</script>

<style>
  .homeHeader {
    background-color: #409EFF;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0px 15px;
    box-sizing: border-box;
  }

  .homeHeader .title {
    font-size: 30px;
    font-family: 华文行楷;
    color: #ffffff;
  }

  .homeHeader .userInfo {
    cursor: pointer;
  }

  .el-dropdown-link img {
    width: 48px;
    height: 48px;
    border-radius: 24px;
    margin-left: 8px;
  }

  .el-dropdown-link {
    display: flex;
    align-items: center;
  }

  .homeWelcome {
    text-align: center;
    font-size: 30px;
    font-family: 华文行楷;
    color: #409EFF;
    padding-top: 10px;
  }

  .homeRouterView {
    margin-top: 10px;
  }
</style>
