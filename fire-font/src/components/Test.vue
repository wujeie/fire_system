<script setup lang="ts">
import { onMounted, ref } from 'vue'
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
  //得到视频列表
  getVideoList()
})
const indexMethod = (index: number) => {
  return index + 1
}

//得到视频列表
const videoList = ref([])
const getVideoList = async () => {
  try {
    const res = await request.get('http://localhost:8080/video/getVideoList')
    videoList.value = res.data.data
    console.log(videoList.value)
    console.log(res.data)
  } catch (error) {
    console.log(error)
  }
}
//添加视频
const formRef = ref();
const videoAddform = ref({
  title: '',
})
const dialogAddVisible = ref(false)
const addVideo = () => {
  dialogAddVisible.value = true
}
const submitForm = () => {
  dialogAddVisible.value = false
  alert('提交成功')
};

//删除视频
const deleteVideoArticle = (videoId: number) => {

}
//查看视频详情
const showVideoDetail = (videoId: number) => {

}
const uploadData = ref({
  title:''
})
const title = ref('')
const beforeUpload = ()=>{
  uploadData.value.title = title.value
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
        <el-button @click="addVideo">添加视频</el-button>
        <el-table :data="videoList" style="width: 100%">
          <el-table-column type="index" :index="indexMethod" label="序号" width="200" />
          <el-table-column prop="videoId" label="ID" width="260" />
          <el-table-column prop="title" label="标题" width="360" />
          <el-table-column label="视频封面">
            <template #default="scope">
              <img v-if="scope.row.videoCover" :src="'http://localhost:83' + scope.row.videoCover" alt="Video Cover"
                style="width: 100px; height: auto;">
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="创建时间" width="470" />
          <el-table-column label="Operations" width="120">
            <template #default="{ row }">
              <el-button link type="primary" size="small" @click="showVideoDetail(row.videoId)">查看详情</el-button>
              <el-button link type="primary" size="small" @click="deleteVideoArticle(row.videoId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 添加视频表单 -->
        <el-dialog :append-to-body="true" v-model="dialogAddVisible" title="添加视频" width="50%">
          <input type="text" v-model="title" placeholder="请输入视频标题">
          <el-upload class="upload-demo" action="http://localhost:8080/video/upload" :on-success="console.log(1)"
             :data="uploadData"
             :before-upload="beforeUpload"  >
             <el-button size="small" type="primary">点击上传</el-button>
            <!-- 上传按钮等组件 -->
          </el-upload>
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