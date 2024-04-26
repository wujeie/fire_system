<script setup lang="ts">
import { onMounted, ref, reactive } from 'vue'
// 组件注册
import Header from '@/components/Header.vue'
import AsideUser from '@/components/AsideUser.vue'
import AsideAdmin from '@/components/AsideAdmin.vue'
import axios from 'axios';
import request from '@/interceptors/request.js'
import { useRouter } from 'vue-router'
import Home from '@/components/Home.vue'
import router from '@/router';


let roleName = ref('');
let username = ''
const asideSettings = ref({
    isCollapse: true,
    width: '200'
})

let userInfo = ref(
    {
        用户名: '',
        邮箱: '',
        注册时间: ''
    }
)

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
    const storedUsername = localStorage.getItem('username')
    if (storedRoleName) {
        roleName.value = storedRoleName
        username = storedUsername
    } else {
        roleName.value = 'user'
    }
    getUserInfo()
})
const getUserInfo = async () => {
    try {
        const response = await request.get(`http://localhost:8080/user/getUserInfo?username=${username}`)
        userInfo.value.用户名 = response.data.data.username;
        userInfo.value.邮箱 = response.data.data.email;
        userInfo.value.注册时间 = response.data.data.createdAt.split('T')[0];
    } catch (error) {
        console.error('Error fetching user info:', error);
    }
}
const showForm = ref(false)

const form = reactive({
    userId: null,
    username: '',
    roleId: '',
    email: '',
    createdAt: '',
    roleName: 'user'
});

const handleUpdate = () => {
    getUserById();
    async function getUserById() {
        try {
            const res = await request.get(`http://localhost:8080/user/getUserInfo?username=${username}`)
            form.username = res.data.data.username;
            form.email = res.data.data.email;
            showForm.value = true;
        } catch (error) {
            console.log(error)
        }
    }
};
const handleClose = () => {
    showForm.value = false;
};

async function submitForm() {
    try {
        // 在这里发送 POST 请求提交表单数据  
        const res = await request.post('http://localhost:8080/user/updateUser', form);
        if (res.data.code === 1) {
            // 处理成功的响应，比如关闭对话框或显示成功消息  
            alert('更新用户信息成功');
            showForm.value = false; // 关闭对话框  
            // 可能还需要刷新列表或其他UI元素  
            //刷新用户列表
            getUserInfo()
        } else {
            // 处理错误的响应  
            alert('更新用户信息失败');
        }
    } catch (error) {
        console.error('提交表单时发生错误', error);
        alert('更新用户信息失败');
    }
}
</script>


<template>
    <el-container style="min-height: 100vh; min-width: 100vw; overflow: hidden;">
        <!-- 根据roleName动态渲染AsideUser或AsideAdmin -->
        <component :is="roleName === 'user' ? AsideUser : AsideAdmin" :collapse="asideSettings.isCollapse"
            :width="asideSettings.width"></component>
        <el-container style="height: 100vh; width: 100%; display: flex; flex-direction: column;">
            <Header :isCollapse="asideSettings.isCollapse" @changeAside="changeAside"></Header>
            <el-main>
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
                    <el-button link type="primary" size="large"
                        @click="handleUpdate">修改资料</el-button>
                </el-card>
                <el-dialog :append-to-body="true" title="编辑用户" v-model="showForm" width="50%"
                    :close-on-click-modal="false" @close="handleClose">
                    <el-form :model="form" ref="formRef" label-width="120px">
                        <el-form-item label="用户名">
                            <el-input v-model="form.username"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱">
                            <el-input v-model="form.email"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitForm">提交</el-button>
                            <el-button @click="showForm = false">取消</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
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