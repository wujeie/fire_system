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
    getUserAuthrityList()
})
const indexMethod = (index: number) => {
    return index + 1
}
const userAuthrityList = ref([])
const getUserAuthrityList = async () => {
    try {
        const res = await request.get('http://localhost:8080/user/getUserAuthority')
        userAuthrityList.value = res.data.data
    } catch (error) {
        console.log(error)
    }
}

const updateForm = reactive({
    userId: 0,
    username: '',
    roleName: ''
})

const handleRoleChange = (row) => {
    // row 现在包含了当前行的数据，包括 userId 和新的 roleName  
    const userId = row.userId;
    const roleName = row.roleName;
    updateForm.userId = userId
    updateForm.username = row.username
    updateForm.roleName = roleName
    submit()
}
const submit = async () => {
    try {
        const res = await request.post('http://localhost:8080/user/updateUserAuthority', updateForm)
        if (res.data.code === 200) {
            getUserAuthrityList()
        }
    } catch (error) {
        console.log(error)
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
                <el-table :data="userAuthrityList" style="width: 100%">
                    <el-table-column type="index" :index="indexMethod" label="序号" width="487"/>
                    <el-table-column prop="userId" label="用户ID" width="487" />
                    <el-table-column prop="username" label="用户名" width="487" />
                    <el-table-column label="选择权限等级" width="470">
                        <template #default="{ row }">
                            <el-select v-model="row.roleName" @change="handleRoleChange(row)" placeholder="请选择">
                                <el-option label="管理员" value="admin"></el-option>
                                <el-option label="用户" value="user"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                </el-table>
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