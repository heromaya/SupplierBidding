<template>
  <div>
    <el-tabs v-model="activeName" >
      <el-tab-pane label="我的公告" name="first">
        <MyNotice></MyNotice>
      </el-tab-pane>
      <el-tab-pane label="发布公告" name="second  ">
        <AddNotice></AddNotice>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>

<script>
  import AddNotice from "./noticemanager/AddNotice";
  import MyNotice from "./noticemanager/MyNotice";
  export default {
    name: "NoticeManager",
    components: {MyNotice, AddNotice},
    data() {
      return {
        activeName: 'first'
      };
    },
  }
</script>

<style scoped>

</style>
