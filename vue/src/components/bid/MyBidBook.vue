<template>
  <div>
    <template>
      <el-tabs v-model="activeName">
        <el-tab-pane label="我的投标文件" name="first">
          <MyBidBookList></MyBidBookList>
        </el-tab-pane>
        <el-tab-pane label="编制投标文件" name="second">
          <MakeBidBook></MakeBidBook>
        </el-tab-pane>

      </el-tabs>
    </template>
  </div>
</template>

<script>
  import MyBidBookList from "./makeBidBook/MyBidBookList";
  import MakeBidBook from "./makeBidBook/MakeBidBook";

  export default {
    name: "MyBidBook",
    components: {MakeBidBook, MyBidBookList}, data() {
      return {
        activeName: 'first'
      };
    },
  }
</script>

<style scoped>

</style>
