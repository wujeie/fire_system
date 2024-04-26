<script setup lang="ts">
import { onMounted, ref, reactive, computed } from 'vue'
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
  getMessages()
})
const indexMethod = (index: number) => {
  return index + 1
}
const messages = ref([])
const getMessages = async () => {
  const res = await request.get('http://localhost:8080/message/selectAll')
  messages.value = res.data.data
}
//限制留言内容的长度
const truncateContent = (content: string) => {  
      const maxLength = 20; // 最大长度  
      return content.length > maxLength ? `${content.substring(0, maxLength)}...` : content;  
};
//展示留言和回复
const dialogDetailVisible = ref(false)
const messageDetailForm = reactive({
  content: '',
  adminReply:''
})
const getMessageDetailByFeedBackId = async (feedbackId:number)=>{
  const res = await request.get('http://localhost:8080/message/getSelectById',{params:{feedbackId}})
  messageDetailForm.content = res.data.data.content
  messageDetailForm.adminReply = res.data.data.adminReply
}
const ShowMessage = (feedbackId:number)=>{
  getMessageDetailByFeedBackId(feedbackId)
  dialogDetailVisible.value = true
}
//回复
const dialogReplyVisible = ref(false)
const messageReplyForm = reactive({
  feedbackId: null,
  content: '',
  adminReply:''
})
const replyMessage = (row)=>{
  messageReplyForm.feedbackId = row.feedbackId
  messageReplyForm.content = row.content
  dialogReplyVisible.value = true
}
const submitReplyForm = async ()=>{
  const res = await request.put('http://localhost:8080/message/reply',messageReplyForm)
  if(res.data.code === 200){
    dialogReplyVisible.value = false
    resetReplyForm()
    getMessages()
  }
}
//重置回复表单
function resetReplyForm() { 
  messageReplyForm.adminReply = '';
}
//删除留言
const deleteMessage = async (feedbackId:number)=>{
  const res = await request.delete('http://localhost:8080/message/delete',{params:{feedbackId}})
  if(res.data.code === 200){
    getMessages()
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
        <el-table :data="messages" style="width: 100%">
          <el-table-column type="index" :index="indexMethod" label="序号" width="200" />
          <el-table-column prop="feedbackId" label="留言ID" width="250"></el-table-column>
          <el-table-column prop="userId" label="用户ID" width="250"></el-table-column>
          <el-table-column prop="content" label="留言内容" width="300">
            <template #default="scope">
              <span>{{ truncateContent(scope.row.content) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="创建时间" width="300"></el-table-column>
          <el-table-column label="回复时间" width="350">
            <template #default="scope">
              <span v-if="scope.row.repliedAt">{{ scope.row.repliedAt }}</span>
              <span v-else>暂未回复</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <span v-if="scope.row.repliedAt"><el-button @click="ShowMessage(scope.row.feedbackId)">查看详情</el-button></span>
              <span v-else><el-button @click="replyMessage(scope.row)">回复</el-button></span>
              <el-button type="danger" @click="deleteMessage(scope.row.feedbackId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 留言详情表单 -->
        <el-dialog :append-to-body="true" v-model="dialogDetailVisible" title="文章详细" width="50%">
          <el-form :model="messageDetailForm" ref="detailFormRef" label-width="120px">
            <el-form-item label="留言内容">
              <el-input type="textarea" v-model="messageDetailForm.content" :rows="15" disabled></el-input>
            </el-form-item>
            <el-form-item label="回复内容">
              <el-input type="textarea" v-model="messageDetailForm.adminReply" :rows="15" disabled></el-input>
            </el-form-item>
          </el-form>
        </el-dialog>
        <!--  留言回复表单-->
        <el-dialog :append-to-body="true" v-model="dialogReplyVisible" title="回复留言" width="50%">
          <el-form :model="messageReplyForm" ref="detailFormRef" label-width="120px">
            <el-form-item label="留言内容">
              <el-input type="textarea" v-model="messageReplyForm.content" :rows="15" disabled></el-input>
            </el-form-item>
            <el-form-item label="回复内容">
              <el-input type="textarea" v-model="messageReplyForm.adminReply" :rows="15"></el-input>
            </el-form-item>
          </el-form>
          <el-form-item>
            <el-button type="primary" @click="submitReplyForm">提交</el-button>
            <el-button @click="dialogReplyVisible = false">取消</el-button>
          </el-form-item>
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