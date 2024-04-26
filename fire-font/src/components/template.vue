<script setup lang="ts">
import { onMounted, ref,reactive } from 'vue'
// 组件注册
import Header from '@/components/Header.vue'
import AsideUser from '@/components/AsideUser.vue'
import AsideAdmin from '@/components/AsideAdmin.vue'
import axios from 'axios';
import request from '@/interceptors/request.js'
import {useRouter} from 'vue-router'
import Home from '@/components/Home.vue'
import router from '@/router';


let roleName = ref('');

const asideSettings = ref({
  isCollapse: true,
  width: '200'
})



// 给子组件绑定事件，通过子组件emit从而改变父组件的值
const changeAside = () => {
  asideSettings.value.isCollapse = !asideSettings.value.isCollapse
  if (asideSettings.value.isCollapse) {
    asideSettings.value.width = "64"
  } else {
    asideSettings.value.width = "200"
  }
}

onMounted(() => {
  //从localStorage中获取到roleName
  const storedRoleName = localStorage.getItem('roleName')
  if (storedRoleName) {
    roleName.value = storedRoleName
  } else {
    roleName.value = 'user'
  }
})

</script>


<template>
  <el-container style="min-height: 100vh; min-width: 100vw; overflow: hidden;">
    <!-- 根据roleName动态渲染AsideUser或AsideAdmin -->
    <component :is="roleName === 'user' ? AsideUser : AsideAdmin" :collapse="asideSettings.isCollapse"
      :width="asideSettings.width"></component>
    <el-container style="height: 100vh; width: 100%; display: flex; flex-direction: column;">
      <Header :isCollapse="asideSettings.isCollapse" @changeAside="changeAside"></Header>
      <el-main>

      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
body {
  background-color: #eee;
  max-width: 100vw;
  overflow: hidden;
}

.el-main {
  width: 100%;
  padding: 0;
  height: 100vh;
  overflow-x: hidden;
}
</style>