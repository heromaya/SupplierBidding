<template>
    <div>
      <template>
        <el-tabs v-model="activeName" >
          <el-tab-pane label="我的招标文件列表" name="first">
            <MyTenderingFileList>
            </MyTenderingFileList>
          </el-tab-pane>
          <el-tab-pane label="招标文件购买申请" name="second">
            <SupplierBuyBookList></SupplierBuyBookList>
          </el-tab-pane>
          <el-tab-pane label="新增招标文件" name="third">
          <AddTenderingFile></AddTenderingFile>
          </el-tab-pane>
        </el-tabs>
      </template>
    </div>
</template>

<script>
    import AddTenderingFile from "./TenderingFile/AddTenderingFile";
    import MyTenderingFileList from "./TenderingFile/MyTenderingFileList";
    import SupplierBuyBookList from "./TenderingFile/SupplierBuyBookList";
    export default {
        name: "TenderingFile",
      components: {SupplierBuyBookList, MyTenderingFileList, AddTenderingFile},
      data() {
        return {
          activeName: 'first'
        };
      },
    }
</script>

<style scoped>

</style>
