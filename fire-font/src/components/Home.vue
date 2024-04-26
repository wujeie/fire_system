<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axios from 'axios';
import request from '@/interceptors/request.js'

const asideSettings = ref({
  isCollapse: true,
  width: '200'
})
let roleName = ref('');
let username = ''
let Unreply = ref(0)
let Hasreply = ref(0)
let userInfo = ref(
  {
    用户名: '',
    邮箱: '',
    注册时间: ''
  }
)

onMounted(() => {
  //从localStorage中获取到roleName
  const storedRoleName = localStorage.getItem('roleName')
  const storedUsername = localStorage.getItem('username')
  if (storedRoleName && storedUsername) {
    roleName.value = storedRoleName
    username = storedUsername
  } else {
    roleName.value = 'user'
  }
  if (roleName.value === 'admin') {
    getUnreplyCount()
  } else if (roleName.value === 'user') {
    getHasreplyCount()
  }

  getUserInfo()
})

const getUserInfo = async () => {
  try {
    const response = await request.get(`http://localhost:8080/user/getUserInfo?username=${username}`)
    userInfo.value.用户名 = response.data.data.username;
    userInfo.value.邮箱 = response.data.data.email;
    userInfo.value.注册时间 = response.data.data.createdAt.split('T')[0];
    localStorage.setItem('userId', response.data.data.userId)
    // console.log('User info:', userInfo.value);
  } catch (error) {
    console.error('Error fetching user info:', error);
  }
}

const getUnreplyCount = async () => {
  try {
    const response = await request.get(`http://localhost:8080/message/getUnResponsesCount`)
    Unreply.value = response.data.data
  } catch (error) {
    console.error('Error fetching unreply count:', error);
  }
}

const getHasreplyCount = async () => {
  try {
    const response = await request.get(`http://localhost:8080/message/getHasResponsesCount`)
    Hasreply.value = response.data.data
  } catch (error) {
    console.error('Error fetching hasreply count:', error);
  }
}

</script>


<template>
  <el-container style="min-height: 100vh; min-width: 100vw; overflow: hidden;">
    <!-- 根据roleName动态渲染AsideUser或AsideAdmin -->
    <el-container style="height: 100vh; width: 100%; display: flex; flex-direction: column;">
      <el-main>
        <div>
          <el-card>
            <h1 style="font-size: 1.3rem;">消防知识科普系统</h1>
          </el-card>
        </div>
        <div>
          <el-card style="max-width: 100%">
            <template #header>
              <div class="card-header">
                <span>个人信息 </span>
              </div>
            </template>
            <p>用户名：{{ userInfo.用户名 }}</p>
            <p>邮箱：{{ userInfo.邮箱 }}</p>
            <p>注册时间：{{ userInfo.注册时间 }}</p>
            <template #footer>当前身份:{{ roleName === 'user' ? '普通用户' : '管理员' }}</template>
          </el-card>
          <!-- 这里放置一片空白区域用于美观 -->
          <!-- 管理员显示为回复的留言 -->
          <el-card v-if="roleName === 'admin'" style="max-width: 100%">
            <template #header>
              <div v-if="roleName === 'admin'" class="card-header">
                <span>未回复的留言数：{{ Unreply }}</span>
              </div>
            </template>
            <el-button type="primary" @click="$router.push('admin/message')">处理</el-button>
          </el-card>
          <!-- 用户显示最新的回复 -->
          <el-card v-if="roleName === 'user'" style="max-width: 100%">
            <template #header> 
              <div v-if="roleName === 'user'" class="card-header">
                <span>最新留言回复数目：{{ Hasreply }}</span>
              </div>
            </template>
            <el-button type="primary" @click="$router.push('user/message')">查看</el-button>
          </el-card>
        </div>
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