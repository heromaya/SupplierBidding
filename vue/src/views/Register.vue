<template>
  <div>
    <el-form
      :rules="rules"
      ref="registerForm"
      :model="registerForm"
      class="registerContainer">
      <h3 class="registerTitle">供应商注册</h3>
      <el-form-item prop="companyName">
        <span class="content">公司名称</span>
        <el-input size="normal" type="text" v-model="registerForm.companyName" auto-complete="off"
                  placeholder="请输入公司名称"></el-input>
      </el-form-item>
      <el-form-item prop="address">
        <span class="content">公司地址</span>
        <el-input size="normal" type="text" v-model="registerForm.address" auto-complete="off"
                  placeholder="请输入公司地址"></el-input>
      </el-form-item>
      <el-form-item prop="userid">
        <span class="content">用户名</span>
        <el-input size="normal" type="text" v-model="registerForm.userid" auto-complete="off"
                  placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <span class="content">密码</span>
        <el-input size="normal" type="password" v-model="registerForm.password" auto-complete="off"
                  placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="taxid">
        <span class="content">统一社会信用代码</span>
        <el-input size="normal" type="text" v-model="registerForm.taxid" auto-complete="off"
                  placeholder="请输入税务登记号"></el-input>
      </el-form-item>
      <el-form-item prop="telephone">
        <span class="content">手机号</span>
        <el-input size="normal" type="number" maxlength="11" v-model= "registerForm.telephone"  placeholder="请输入手机号" auto-complete="off" @blur="checkTelephone"></el-input>
      </el-form-item>
      <el-form-item prop="email">
        <span class="content">邮箱</span>
        <el-input size="normal" type="email"  v-model= "registerForm.email" placeholder="请输入邮箱" auto-complete="off" @blur="checkEmail"></el-input>
      </el-form-item>
      <a @click="login" class="login">去登录</a>
      <el-button size="normal" type="primary" style="width: 100%;" @click="submitRegister">注册</el-button>
    </el-form>
  </div>
</template>

<script>

  export default {
    name: "Register",
    data() {
      return {
        loading: false,
        registerForm: {
          companyName: 'test',
          address: 'test',
          userid: 'gys3',
          password: 'gys3',
          taxid: 'gys3',
          telephone: '15270483502',
          email: '1527048352@qq.com'
        },
        rules: {
          name:[{required: true, message: '请输入公司名称', trigger: 'blur'}],
          address:[{required: true, message: '请输入公司地址', trigger: 'blur'}],
          userid: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'}],
          taxid: [{required: true, message: '请输入统一社会信用代码', trigger: 'blur'}],
          telephone: [{required: true, message: '请输入手机号', trigger: 'blur'}],
          email: [{required: true, message: '请输入邮箱', trigger: 'change'}],
        },
        responseResult: []
      }
    },
    methods: {
      submitRegister() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            this.postKeyValueRequest('/register',this.registerForm).then(resp=>{
              if(resp.status == 200){
               this.$router.replace('/');
              }
            })
          }else{
            this.$message.error("请输入所有字段！")
            return  false
          }
        });
      },
      login(){
        this.$router.replace('/')
      },
      checkTelephone(){
        var regTel = /^1[0-9]{10}$/
        if(this.registerForm.telephone != '' && !regTel.test(this.registerForm.telephone)){
          this.$message({
            message: '手机格式不正确',
            type: 'error'
          })
          this.registerForm.telephone = ''
        }
      },
      checkEmail(){
        var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
        if (this.registerForm.email != '' && !regEmail.test(this.registerForm.email)) {
          this.$message({
            message: '邮箱格式不正确',
            type: 'error'
          })
          this.registerForm.email = ''
        }
      }

    }
  }
</script>

<style>
  .registerContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 450px;
    padding: 15px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .registerTitle {
    margin: 15px auto 20px auto;
    text-align: center;
    color: #505458;
  }


  .el-form-item__content{
    display: flex;
    align-items: center;
  }
  .register{
    display: block;
    text-align: right;
    width: 100%;
    height: 30px;
    line-height: 30px;
    cursor: pointer;
    margin-bottom: 20px;
  }
  .login{
    display: block;
    text-align: center;
    width: 100px;
    float: right;
    height: 20px;
    line-height: 20px;
    cursor: pointer;
    margin-bottom: 20px;

  }
  .login:hover{
    color:#ff4400;
    text-decoration: underline;
  }
  .content{
    display: block;
    width: 200px;
    height: 100%;
    text-align: center;
    color:#505458;
    font-weight: bold;
    letter-spacing: 2px;
  }
</style>
