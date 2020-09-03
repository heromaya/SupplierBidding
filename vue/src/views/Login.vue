<template>
  <div>
    <el-form
      :rules="rules"
      ref="loginForm"
      v-loading="loading"
      element-loading-text="正在登录..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      :model="loginForm"
      class="loginContainer">
      <h3 class="loginTitle">招投标系统登录</h3>
      <el-form-item prop="code">
        <el-input size="normal" type="text" v-model="loginForm.code" auto-complete="off"
                  placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off"
                  placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
      </el-form-item>
      <el-checkbox class="login_remember" v-model="loginForm.checked"
                   label-position="left" ><span style="color: #505458">记住密码</span></el-checkbox>
      <a @click="register" class="register">供应商注册</a>
      <el-button size="normal" type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
    </el-form>
  </div>
</template>

<script>

  export default {
    name: "Login",
    data() {
      return {
        loading: false,
        loginForm: {
          code: '',
          password: '',
          checked:false
        },
        rules: {
          code: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        },
      }
    },
    methods: {
      submitLogin() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.postKeyValueRequest('/login',this.loginForm).then(resp=>{
              if(resp){
                this.$store.commit('INIT_CURRENTUSER', resp.obj);
                window.sessionStorage.setItem("user",JSON.stringify(resp.obj));
                let path = this.$route.query.redirect;
                if(path == '/' || path == undefined){
                  this.$router.replace('/home')
                }else{
                  this.getRequest(path).then(response =>{
                    if(response == undefined){
                      this.$router.replace('/home');
                    }else{
                      this.$router.replace(path);
                    }

                  })
                }
              }
            })
          }else{
            this.$message.error("请输入所有字段！")
            return  false
          }
        });
      },
      register(){
        this.$router.replace('/register')
      }

    }
  }
</script>

<style>
  .loginContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 15px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .loginTitle {
    margin: 15px auto 20px auto;
    text-align: center;
    color: #505458;
  }

  .loginRemember {
    text-align: left;
    margin: 0px 0px 15px 0px;
  }
  .el-form-item__content{
    display: flex;
    align-items: center;
  }
  .register{
    float: right;
    display: block;
    text-align: center;
    width: 100px;
    height: 30px;
    line-height: 30px;
    cursor: pointer;
    margin-bottom: 20px;
  }
  .register:hover{
    color:#ff4400;
    text-decoration: underline;
  }
</style>
