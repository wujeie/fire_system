<script setup>
import { ref } from 'vue'

import { ElMessage } from 'element-plus';
import a11Img from '@/assets/a11.jpeg'  // 导入assets下的图片，自己喜欢啥图加啥图就行
const isCollapse = ref(true)
// 获取父组件传递过来的数据 
const showIcon = defineProps({
    isCollapse: Boolean
})

// 获取父组件自定义的事件
const emit = defineEmits(['changeAside'])

// 自定义按钮点击事件，侧边栏的收缩按钮
const collapseAside = () => {
    emit('changeAside')
}

// 登出按钮
const LogOut = ()=>{}

const username = localStorage.getItem('username')
const avatarUrl = localStorage.getItem('avatarUrl')

</script>


<template>
    <div>
        <el-header style="display: flex;font-size: 16px;">
            <div style="display: inline-flex;flex: 1; align-items: center;justify-content: left;">
                <el-icon v-show="!showIcon.isCollapse" @click="collapseAside" style="color: #303133;">
                    <Fold />
                </el-icon>
                <el-icon v-show="showIcon.isCollapse" @click="collapseAside" style="color: #303133;">
                    <Expand />
                </el-icon>
            </div>
            <div class="toolbar"  >
                <div class="block" style="margin-right: 10px;">
                    <el-avatar :size="40" :src="avatarUrl" />
                </div>
                <span>{{ username }}</span>
                <el-dropdown trigger="click">
                    <el-icon style="margin-left: 18px; margin-top: 1px;color: #303133;">
                        <setting />
                    </el-icon>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>View</el-dropdown-item>
                            <el-dropdown-item>Add</el-dropdown-item>
                            <el-dropdown-item @click="LogOut">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </el-header>
    </div>
</template>



<style scoped>
.el-header {
    width: 100%;
    background-color: #fff;
    color: var(--el-text-color-primary);
    box-shadow: var(--el-box-shadow);
}

.toolbar {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    right: 20px;
    text-align: right;
}

.el-dropdown-menu__item {
    width: 120px;
}

.icon-color {
    color: white;
}
</style>
