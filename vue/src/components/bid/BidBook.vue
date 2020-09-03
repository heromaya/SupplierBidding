<template>
  <div>
    <template>
      <el-tabs v-model="activeName">
        <el-tab-pane label="购买列表" name="first">
          <BidBookList></BidBookList>
        </el-tab-pane>
        <el-tab-pane label="申请购买招标书" name="second">
          <BuyTenderingBook></BuyTenderingBook>
        </el-tab-pane>

      </el-tabs>
    </template>
  </div>
</template>

<script>
  import BuyTenderingBook from "./BidBook/BuyTenderingBook";
  import BidBookList from "./BidBook/BidBookList";

  export default {
    name: "BidBook",
    components: {BidBookList, BuyTenderingBook},
    data() {
      return {
        activeName: 'first'
      };
    },
  }
</script>

<style scoped>

</style>
