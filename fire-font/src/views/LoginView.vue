<template>
    <div class="login-container">
        <el-form class="login-form" :model="loginForm" label-width="80px">
            <el-form-item label="用户名">
                <el-input v-model="loginForm.username"/>
            </el-form-item>
            <el-form-item label="密码">
                <el-input type="password" v-model="loginForm.password"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleSubmit">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup lang='ts'>
import { ref, reactive, toRefs, onBeforeMount, onMounted, watchEffect, computed, getCurrentInstance } from 'vue';
import axios from 'axios'
import router from '@/router'

const loginForm = reactive({
    username: '',
    password: ''
})

const handleSubmit = async () => {  
      try {  

        // 发送POST请求到后端登录接口  
        const response = await axios.post('http://localhost:8080/user/login', loginForm);  
        if (response.status !== 200) {  
          // 处理错误，例如显示错误信息给用户  
          console.error('登录失败', response); 
          alert('登录失败请检查用户名和密码是否正确') 
          return;  
        }
        // 假设后端返回的数据中包含token字段  
        const token = response.data.data.token;
        const roleName = response.data.data.roleName;
        const username = response.data.data.username;
        const avatarUrl = response.data.data.avatarUrl;
        // console.log(token)
        // 存储token到localStorage或sessionStorage
        localStorage.setItem('userToken', token); 
        localStorage.setItem('roleName', roleName); 
        localStorage.setItem('username',username);
        localStorage.setItem('avatarUrl',avatarUrl);
  
        // 导航到应用的主页或其他受保护的路由
        // 这里你可能需要使用Vue Router进行路由跳转
        
        router.push('/home'); // 假设你有一个名为'HomePage'的路由  
  
        // 登录成功后的其他操作...   
      } catch (error) {  
        // 处理错误，例如显示错误信息给用户  
        console.error('登录失败', error);  
        // 可以根据后端返回的错误信息来定制错误提示  
        alert('登录失败请检查用户名和密码是否正确')
      } 
    };  


</script>
<style scoped>
.login-container {  
  /* 确保容器占据整个视口 */  
  height: 100vh;  
  width: 100vw;
  display: flex;  
  justify-content: center;  
  align-items: center;  
  /* 背景图片设置 */  
  background-image: url('@/assets/loginBackground.jpg'); /* 替换为你的图片路径 */  
  background-size: 100%; /* 图片覆盖整个容器 */  
  background-attachment: fixed;/* 固定背景图片 */
}  
.login-form {
    /* 为登录框添加样式 */
    width: 300px;
    padding: 20px;
    background-color: rgba(255, 255, 255, 0.8);
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>