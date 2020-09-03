<template>
  <div>
    <template>
      <el-tabs v-model="activeName">
        <el-tab-pane label="我的待评项目" name="first">
          <MyCommontProject></MyCommontProject>
        </el-tab-pane>
        <el-tab-pane label="填写评标书" name="second">
          <WriterExpertBook></WriterExpertBook>
        </el-tab-pane>
      </el-tabs>
    </template>
  </div>
</template>

<script>
  import MyCommontProject from "./expertManager/MyCommontProject";
  import WriterExpertBook from "./expertManager/WriterExpertBook";

  export default {
    name: "ExpertManager",
    components: {WriterExpertBook, MyCommontProject},
    data(){
      return{
        activeName: 'first'
      }
    }
  }
</script>

<style scoped>

</style>
