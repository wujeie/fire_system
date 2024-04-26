<script setup>
import { ref } from 'vue'

const settings = defineProps({
  collapse: Boolean,
  width: String
})

const iconColor = ref('color:#F8FAFB')
</script>


<template>
  <el-aside :width="settings.width">
    <el-scrollbar>
      <el-menu :default-openeds="['1', '3']" 
      :collapse="settings.collapse" active-text-color="#F8FAFB"
        background-color="#1A1C1E" text-color="#74767A" router="true">
        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <House />
            </el-icon><span>主页</span>
          </template>
          <el-menu-item index="/home">主页</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <User />
            </el-icon><span>用户管理</span>
          </template>
          <el-menu-item index="/admin/message">用户管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <Menu />
            </el-icon><span>内容管理</span>
          </template>
          <el-menu-item index="/admin/article">文章管理</el-menu-item>
          <el-menu-item index="/admin/video">视频管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="4">
          <template #title>
            <el-icon>
              <Message />
            </el-icon><span>测试管理</span>
          </template>
          <el-menu-item index="/admin/question">试题管理</el-menu-item>
          <el-menu-item index="/admin/testPaper">试卷管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title>
            <el-icon>
              <Files />
            </el-icon><span>权限管理</span>
          </template>
          <el-menu-item index="/admin/authrity">权限管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="6">
          <template #title>
            <el-icon><ChatSquare /></el-icon><span>留言管理</span>
          </template>
          <el-menu-item index="/admin/message">留言管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-scrollbar>
  </el-aside>
</template>


<style scoped>
.el-aside {
  height: 100vh;
  overflow: hidden;
  box-shadow: var(--el-box-shadow-lighter);
}

.el-scrollbar {
  height: 100vh;
  overflow: hidden;
  background-color: #1A1C1E;
}

.el-menu {

  border-right: none;
}
</style>
