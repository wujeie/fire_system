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
import { vi } from 'element-plus/es/locale/index.mjs';


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
    getVideoList()
    alert('提交成功')
};

const beforeUpload = (file: File) => {
    var fileSize = file.size / 1024 / 1024 < 50;
    if (!fileSize) {
        alert("视频大小不能超过50MB");
        return false;
    }
    return true;
}

//删除视频
const deleteVideoArticle = (videoId: number) => {
    deleteVideo(videoId)
    getVideoList()
}
const deleteVideo = async (videoId: number) => {
    try {
        const res = await request.delete('http://localhost:8080/video/deleteVideo', {
            params: {
                videoId: videoId
            }
        })
        getVideoList()
    } catch (error) {
        console.log(error)
    }
}
//查看视频详情
const showVideoDialog = ref(false)
const videoUrl = ref('')
const showVideoDetail = (videoId: number) => {
    getVideo(videoId);
    showVideoDialog.value = true
}
const getVideo = async (videoId: number) => {
    try {
        const res = await request.get('http://localhost:8080/video/getVideoById', {
            params: {
                videoId: videoId
            }
        })
        videoUrl.value = 'http://localhost:83'+res.data.data.filePath
    } catch (error) {
        console.log(error)
    }
}
//关闭视频 
const handleVideoDialogClose = ()=>{
    let myVideo = document.getElementById('myVideo') as HTMLVideoElement
    myVideo.pause()
    myVideo.currentTime = 0
    showVideoDialog.value=false
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
                    <el-table-column label="视频封面" width="500">
                        <template #default="scope">
                            <img v-if="scope.row.videoCover" :src="'http://localhost:83' + scope.row.videoCover"
                                alt="Video Cover" style="width: 100px; height: auto;">
                        </template>
                    </el-table-column>
                    <el-table-column prop="createdAt" label="创建时间" width="470" />
                    <el-table-column label="Operations" width="120">
                        <template #default="{ row }">
                            <el-button link type="primary" size="small"
                                @click="showVideoDetail(row.videoId)">查看详情</el-button>
                            <el-button link type="primary" size="small"
                                @click="deleteVideoArticle(row.videoId)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 添加视频表单 -->
                <el-dialog :append-to-body="true" v-model="dialogAddVisible" title="添加视频" width="50%">
                    <el-form :model="videoAddform" ref="formRef" label-width="120px">
                        <el-form-item label="视频标题">
                            <el-input v-model="videoAddform.title"></el-input>
                        </el-form-item>
                        <el-form-item label="上传视频">
                            <el-upload class="upload-demo" action="http://localhost:8080/video/upload" multiple
                                :on-success="console.log('上传成功')" :data="videoAddform" :before-upload="beforeUpload">
                                <el-button size="small" type="primary">点击上传</el-button>
                            </el-upload>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitForm">提交</el-button>
                            <el-button @click="dialogAddVisible = false">取消</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
                <!-- 查看视频详细弹出播放框播放视频 -->
                <el-dialog v-model="showVideoDialog" width="50%" @close="handleVideoDialogClose">
                    <span slot="title">视频播放</span>
                    <video id = 'myVideo' :src="videoUrl" controls width="100%" height="600px" @ended="showVideoDialog = false"></video>
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