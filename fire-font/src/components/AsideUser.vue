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
            </el-icon><span>首页</span>
          </template>
          <el-menu-item index="/home">首页</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <Menu />
            </el-icon><span>消防知识</span>
          </template>
          <el-menu-item index="/user/article">文章浏览</el-menu-item>
          <el-menu-item index="/user/video">视频浏览</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <notebook />
            </el-icon><span>在线测试</span>
          </template>
          <el-menu-item index="/user/test">在线测试</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="4">
          <template #title>
            <el-icon>
              <user />
            </el-icon><span>个人中心</span>
          </template>
          <el-menu-item index="/user/alter">修改资料</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title>
              <el-icon><ChatSquare /></el-icon>
                <span>反馈留言</span>
          </template>
          <el-menu-item index="/user/message">反馈留言</el-menu-item>
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
