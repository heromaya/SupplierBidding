<template>
  <div>
    <div class="roleInfo">
      <el-table
        :data="roles"
        border
        stripe
        size="small"
        @selection-change="handleSelectionChange"
        style="width: 100%">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="urid"
          label="urid"
          align="center"
          v-if="0==1">
        </el-table-column>
        <el-table-column
          prop="code"
          label="代码"
          align="center"
          width="180">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
          align="center">
        </el-table-column>
        <el-table-column
          prop="createdby"
          label="创建人"
          align="center">
        </el-table-column>
        <el-table-column
          prop="createdon"
          width="180"
          label="创建时间"
          align="center">
        </el-table-column>
        <el-table-column
          prop="updatedby"
          label="修改人"
          align="center">
        </el-table-column>
        <el-table-column
          prop="updatedon"
          label="修改时间"
          width="180"
           align="center">
        </el-table-column>
        <el-table-column label="操作"  align="center" width="180">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="showEditView(scope.$index, scope.row)" >编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button @click="deleteMany" type="danger" size="small" style="margin-top: 10px;" :disabled="multipleSelection.length==0">批量删除</el-button>
    </div>
    <el-dialog
      title="修改角色信息"
      :visible.sync="dialogVisible"
      width="20%">
      <div>
        <p>
          <el-tag>代码</el-tag>
          <el-input class="updateRoleInput" v-model="updateRole.code" size="small">
            <template slot="prepend">ROLE_</template>
          </el-input>
        </p>
        <p>
          <el-tag class="updateName">名称</el-tag>
          <el-input class="updateRoleInput ,updateName" v-model="updateRole.name" size="small"></el-input>
        </p>

      </div>
      <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible=false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Role",
    data() {
      return {
        role: {
          code: '',
          name: ''
        },
        dialogVisible: false,
        updateRole: {
          code: '',
          name: '',
        },
        roles: [],
        multipleSelection: []
      }
    },
    mounted() {
      this.initRoles()
    },
    methods: {
      deleteMany(){
        this.$confirm('此操作将永久删除【' + this.multipleSelection.length + '】条记录，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let urids = '?';
          this.multipleSelection.forEach(item=>{
            urids += 'urids='+item.urid+'&'
          })
          this.deleteRequest('/system/roleinfo/' + urids).then(resp => {
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
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      initRoles() {
        this.getRequest("/system/roleinfo/").then(resp => {
          if (resp) {
            this.roles = resp;
          }
        })
      },
      showEditView(index, data) {
        //拷贝
        Object.assign(this.updateRole,data);
        var showcode = this.updateRole.code.substr(5)
        this.updateRole.code = showcode
        this.dialogVisible  = true
      },
      doUpdate() {
        this.putRequest('/system/roleinfo/', this.updateRole).then(resp => {
          if (resp) {
            this.initRoles();
            this.updateRole.code = '';
            this.updateRole.name = '';
            this.dialogVisible = false;
          }
        })
      },
      handleDelete(index, data) {
        this.$confirm('此操作将永久删除【' + data.name + '】是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/system/roleinfo/' + data.urid).then(resp => {
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
      }
    }
  }
</script>

<style scoped>
  .roleInfo {
    margin-top: 20px;
  }

  .updateRoleInput {
    width: 80%;
    margin-left: 5%;
  }

  .updateName {
    margin-top: 20px;
  }
</style>
