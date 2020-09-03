<template>
  <div>
    <template>
      <el-tabs v-model="activeName">
        <el-tab-pane label="专家组列表" name="first">
          <ExpertGroupList></ExpertGroupList>
        </el-tab-pane>
        <el-tab-pane label="组建专家组" name="second">
          <BuildExpertGroup></BuildExpertGroup>
        </el-tab-pane>

      </el-tabs>
    </template>
  </div>
</template>

<script>
  import BuildExpertGroup from "./expertGroup/BuildExpertGroup";
  import ExpertGroupList from "./expertGroup/ExpertGroupList";
  export default {
    name: "ExpertGroup",
    components: {ExpertGroupList, BuildExpertGroup},
    data() {
      return {
        activeName: 'first'
      };
    }
  }
</script>

<style scoped>

</style>
