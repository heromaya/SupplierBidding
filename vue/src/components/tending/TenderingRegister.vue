<template>
    <div>
      <template>
        <el-tabs v-model="activeName" >
          <el-tab-pane label="我的招标计划列表" name="first">
            <TenderingProject></TenderingProject>
          </el-tab-pane>
          <el-tab-pane label="项目报名列表" name="second">
            <ApplySupplierList></ApplySupplierList>
          </el-tab-pane>
          <el-tab-pane label="招标计划申请" name="third">
            <TenderingProjectRegister></TenderingProjectRegister>
          </el-tab-pane>

        </el-tabs>
      </template>
    </div>
</template>

<script>
    import TenderingProjectRegister from "./TenderingRegister/TenderingProjectRegister";
    import TenderingProject from "./TenderingRegister/TenderingProject";
    import ApplySupplierList from "./TenderingRegister/ApplySupplierList";
    export default {
        name: "TenderingRegister",
      components: {ApplySupplierList, TenderingProject, TenderingProjectRegister},
      data() {
        return {
          activeName: 'first'
        };
      },
    }
</script>

<style scoped>

</style>
