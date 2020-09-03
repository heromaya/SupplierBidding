<template>
    <div>
      <template>
        <el-tabs v-model="activeName" >
          <el-tab-pane label="招标信息列表" name="first">
          <AllTenderingInfo></AllTenderingInfo>
          </el-tab-pane>
          <el-tab-pane label="报名列表" name="second">
          <MyConfirmProject></MyConfirmProject>
          </el-tab-pane>

        </el-tabs>
      </template>
    </div>
</template>

<script>
    import AllTenderingInfo from "./TenderingInfo/AllTenderingInfo";
    import MyConfirmProject from "./TenderingInfo/MyConfirmProject";
    export default {
        name: "TenderingInfo",
      components: {MyConfirmProject, AllTenderingInfo},
      data() {
        return {
          activeName: 'first'
        };
      },
    }
</script>

<style scoped>

</style>
