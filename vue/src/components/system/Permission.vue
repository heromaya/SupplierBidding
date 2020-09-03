<template>
  <div>
    <div class="addRole">
      <el-input
        size="small"
        style="width: 300px;margin-right: 10px"
        placeholder="添加角色代码"
        prefix-icon="el-icon-plus"
        v-model="role.code">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input
        size="small"
        style="width: 300px;margin-right: 10px"
        placeholder="添加角色名称"
        prefix-icon="el-icon-plus"
        v-model="role.name"
        @keydown.enter.native="handleAdd">
      </el-input>
      <el-button icon="el-icon-plus" size="small" type="primary" @click="handleAdd">添加</el-button>
    </div>
    <div class="permissionMain">
      <el-collapse accordion @change="change" v-model="activeName">
        <el-collapse-item :title="role.name" :name="role.urid" v-for="(role,index) in roles" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问的资源</span>
              <el-button style="float: right; padding: 3px 0;color:#ff0000" type="text"
                         icon="el-icon-delete" @click="deleteRole(role)"></el-button>
            </div>
            <div>
              <el-tree :data="allMenus" :props="defaultProps" show-checkbox node-key="urid"
                       :default-checked-keys="selectedMenus"
                       ref="tree"
                       :key="index"
              ></el-tree>
              <div style="display: flex;justify-content: flex-end">
                <el-button size="mini" @click="cancelUpdate">取消修改</el-button>
                <el-button size="mini" type="primary" @click="doUpdate(role.urid,index)">确认修改</el-button>
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Permission",
    data() {
      return {
        role: {
          urid: '',
          code: '',
          name: ''
        },
        roles: [],
        allMenus: [],
        selectedMenus: [],
        activeName: '',
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      }
    },
    mounted() {
      this.initRoles();
    },
    methods: {
      handleAdd() {
        if (!this.role.code) {
          this.$message.error("代码不能为空！请填写代码！")
          return false;
        }
        if (!this.role.name) {
          this.$message.error("名称不能为空！请填写名称！")
          return false;
        }
        this.postRequest('/system/roleinfo/', this.role).then(resp => {
          if (resp) {
            this.initRoles();
            this.role.code = '';
            this.role.name = '';
          }
        })
      },
      remove(arr,value){
        for(var i = 0 ; i<arr.length;i++){
          if(arr[i]==value){
            arr.splice(i,1);
            break;
          }
        }
      },
      deleteRole(role){
        this.$confirm('此操作将永久删除【' + role.name + '】是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/system/roleinfo/' + role.urid).then(resp => {
            if (resp) {
              this.initRoles();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      cancelUpdate() {
        this.activeName = '-1'
      },
      doUpdate(urid, index) {
        let tree = this.$refs.tree[index];
        let selectedKeys = tree.getCheckedKeys();
        // console.log("全选中的节点："+selectedKeys)
        let hafCheckedKeys = tree.getHalfCheckedKeys();
        // console.log("半选中的节点:"+hafCheckedKeys)
        this.remove(hafCheckedKeys,'1')
        // console.log("删除最顶层的半选中的节点:"+hafCheckedKeys)
        var newSelected = selectedKeys.concat(hafCheckedKeys);
        // console.log("合并后的节点:"+newSelected)
        let url = '/system/permissinfo/?roleid=' + urid;
        newSelected.forEach(key => {
          url += '&menuids=' + key;
        })
        this.putRequest(url).then(resp => {
          if (resp) {
            this.activeName = -1
          }
        })
      },
      change(roleid) {
        if (roleid) {
          this.initMenus();
          this.initSelectMenus(roleid);
        }
      },
      initSelectMenus(roleid) {
        this.getRequest('/system/permissinfo/' + roleid).then(resp => {
          if(resp){
            var parent = [1,2,3,4,5,6,7,8,25];
            for(var i = 0 ; i < parent.length ;i++){
              this.remove(resp,i)
            }
            this.selectedMenus = resp;
          }
        })
      },
      initMenus() {
        this.getRequest('/system/permissinfo/').then(resp => {
          if (resp) {
            this.allMenus = resp;
          }
        })
      },
      initRoles() {
        this.getRequest("/system/roleinfo/").then(resp => {
          if (resp) {
            this.roles = resp
          }
        })
      }
    }
  }
</script>

<style>
  .addRole {
    display: flex;
    justify-content: flex-start;
  }

  .addRole .el-input {
    width: 300px;
    margin-left: 10px;
  }

  .addRole .el-button {
    margin-left: 10px;
  }

  .permissionMain {
    margin-top: 10px;
    width: 700px
  }
</style>
