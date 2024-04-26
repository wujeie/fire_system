<script setup lang="ts">
import { onMounted, reactive, ref, type Ref } from 'vue'
// 组件注册
import Header from '@/components/Header.vue'
import AsideUser from '@/components/AsideUser.vue'
import AsideAdmin from '@/components/AsideAdmin.vue'
import axios from 'axios';
import request from '@/interceptors/request.js'
import { useRouter } from 'vue-router'
import Home from '@/components/Home.vue'
import router from '@/router';

let users = ref([]);

let user = ref({
  userId: 0,
  username: '',
  roleId: '',
  email: '',
  createdAt: '',
  roleName: ''
})

let roleName = ref('');
let username = ''

const asideSettings = ref({
  isCollapse: true,
  width: '200'
})

let showForm = ref(false);

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
  if (storedRoleName && storedUsername) {
    roleName.value = storedRoleName
    username = storedUsername
  } else {
    roleName.value = 'user'
  }
  getUsers()
  console.log(users.value)
})


const getUsers = async () => {
  try {
    const res = await request.get('http://localhost:8080/user/getAllUsers')
    users.value = res.data.data
  } catch (error) {
    console.log(error)
  }
}


const handleClick = () => {
  console.log('click')
}

const form = reactive({
  userId: null,
  username: '',
  roleId: '',
  email: '',
  createdAt: '',
  roleName: ''
});

interface User {
  userId: number;
  username: string;
  email: string;
}

const handleUpdate = (userId: number) => {
  console.log("userId:", userId)
  getUserById();
  async function getUserById() {
    try {
      const res = await request.get(`http://localhost:8080/user/getUserById?userId=${userId}`)
      form.userId = res.data.data.userId;
      form.username = res.data.data.username;
      form.email = res.data.data.email;
      form.roleName = res.data.data.roleName;
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
      getUsers();
    } else {
      // 处理错误的响应  
      alert('更新用户信息失败');
    }
  } catch (error) {
    console.error('提交表单时发生错误', error);
    alert('更新用户信息失败');
  }
}

const showAddForm = ref(false)
const AddForm = reactive({
  username: '',
  email: '',
  roleId:2,
  password:''
})
const addUser = ()=>{
  showAddForm.value = true
}

const submitAddForm = async ()=>{
  try {
    const res = await request.post('http://localhost:8080/user/addUser', AddForm)
    if (res.data.code === 1) {
      alert('添加用户成功')
      showAddForm.value = false
      getUsers()
    } else {
      alert('添加用户失败')
    }
  } catch (error) {
    console.log(error)
    alert('添加用户失败')
  }
}
//删除用户
const handleDelete = async (userId: number) => {
  try {
    const res = await request.delete(`http://localhost:8080/user/deleteUser?userId=${userId}`)
    if (res.data.code === 1) {
      alert('删除用户成功')
      getUsers()
    } else {
      alert('删除用户失败')
    }
  } catch (error) {
    console.log(error)
    alert('删除用户失败')
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
        <el-button @click="addUser">添加用户</el-button>
        <el-table :data="users" style="width: 100%">
          <el-table-column prop="userId" label="用户ID" width="300" />
          <el-table-column prop="username" label="用户名" width="300" />
          <el-table-column prop="email" label="邮箱" width="300" />
          <el-table-column prop="roleName" label="权限等级" width="300" />
          <el-table-column prop="createdAt" label="创建时间" width="570" />
          <el-table-column label="Operations" width="120">
            <template #default="{ row }">
              <el-button link type="primary" size="small" @click=handleUpdate(row.userId)>Edit</el-button>
              <el-button link type="primary" size="small" @click=handleDelete(row.userId)>Delete</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog :append-to-body="true" title="编辑用户" v-model="showForm" width="50%" :close-on-click-modal="false"
          @close="handleClose">
          <el-form :model="form" ref="formRef" label-width="120px">
            <el-form-item label="用户ID">
              <el-input v-model="form.userId" disabled></el-input>
            </el-form-item>
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
        <!-- 添加用户 -->
        <el-dialog :append-to-body="true" title="添加用户" v-model="showAddForm" width="50%" :close-on-click-modal="false"
          @close="handleClose">
          <el-form :model="AddForm" ref="formRef" label-width="120px">
            <el-form-item label="用户名">
              <el-input v-model="AddForm.username"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="AddForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="AddForm.password"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitAddForm">提交</el-button>
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

.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>
