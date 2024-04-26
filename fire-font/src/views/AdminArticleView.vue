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
  getArticleList()
})

const indexMethod = (index: number) => {
  return index + 1
}
//获取文章列表
const articlelist = ref([])

const getArticleList = async () => {
  try {
    const res = await request.get('http://localhost:8080/article/getArticleList')
    articlelist.value = res.data.data
  } catch (error) {
    console.log(error)
  }
}
//查看文章详情
const dialogDetailVisible = ref(false)
const articleDetailForm = reactive({
  title: '',
  content: ''
})
const showArticleDetail = (articleId: number) => {
  dialogDetailVisible.value = true
  getArticleDetail(articleId)
}
const getArticleDetail = async (articleId: number) => {
  try {
    const res = await request.get('http://localhost:8080/article/getArticleDetail', {
      params: {
        articleId: articleId
      }
    })
    articleDetailForm.title = res.data.data.title
    articleDetailForm.content = res.data.data.content
    articleUptateForm.title = res.data.data.title
    articleUptateForm.content = res.data.data.content
    articleUptateForm.articleId = res.data.data.articleId
  } catch (error) {
    console.log(error)
  }
}
//修改文章
const dialogUpdateVisible = ref(false)
const articleUptateForm = reactive({
  articleId: null,
  title: '',
  content: ''
})

const updateArticle = (articleId: number) => {
  getArticleDetail(articleId)
  dialogUpdateVisible.value = true
}

const submitForm = async () => {
  try {
    const res = await request.post('http://localhost:8080/article/updateArticle', articleUptateForm)
    if (res.data.code === 1) {
      alert('更新文章成功')
      dialogUpdateVisible.value = false
      getArticleList()
    } else {
      alert('更新文章失败')
    }
  } catch (error) {
    console.log(error)
    alert('更新文章失败')
  }
}

//添加文章

const dialogAddVisible = ref(false)

const articleAddForm = reactive({
  title: '',
  content: ''
})
function resetAddForm() {  
  articleAddForm.title = '';  
  articleAddForm.content = ''; 
  console.log(articleAddForm) 
}

const addArticle = () => {
  dialogAddVisible.value = true
}
const submitAddForm = async () =>{
  try {
    const res = await request.put('http://localhost:8080/article/addArticle', articleAddForm)
    if (res.data.code === 1) {
      alert('添加文章成功')
      dialogAddVisible.value = false
      resetAddForm()
      getArticleList()
    } else {
      alert('添加文章失败')
    }
  } catch (error) {
    console.log(error)
    alert('添加文章失败')
  }
}

//删除文章
const deleteArticle =async (articleId:number) => {
  try {
    const res = await request.delete('http://localhost:8080/article/deleteArticle', {
      params: {
        articleId: articleId
      }
    })
    if (res.data.code === 1) {
      alert('删除文章成功')
      getArticleList()
    } else {
      alert('删除文章失败')
    }
  } catch (error) {
    console.log(error)
    alert('删除文章失败')
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
        <el-button @click="addArticle">添加文章</el-button>
        <el-table :data="articlelist" style="width: 100%">
          <el-table-column type="index" :index="indexMethod" label="序号" width="200" />
          <el-table-column prop="articleId" label="ID" width="260" />
          <el-table-column prop="title" label="标题" width="360" />
          <el-table-column prop="createdAt" label="创建时间" width="470" />
          <el-table-column prop="updatedAt" label="修改时间" width="470" />
          <el-table-column label="Operations" width="120">
            <template #default="{ row }">
              <el-button link type="primary" size="small" @click="showArticleDetail(row.articleId)">查看详情</el-button>
              <el-button link type="primary" size="small" @click="updateArticle(row.articleId)">修改</el-button>
              <el-button link type="primary" size="small" @click="deleteArticle(row.articleId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 文章详情表单 -->
        <el-dialog :append-to-body="true" v-model="dialogDetailVisible" title="文章详细" width="50%">
          <el-form :model="articleDetailForm" ref="detailFormRef" label-width="120px">
            <el-form-item label="标题">
              <el-input v-model="articleDetailForm.title" disabled></el-input>
            </el-form-item>
            <el-form-item label="内容">
              <el-input type="textarea" v-model="articleDetailForm.content" :rows="30" disabled></el-input>
            </el-form-item>
          </el-form>
        </el-dialog>


        <!-- 修改文章表单 -->
        <el-dialog :append-to-body="true" v-model="dialogUpdateVisible" title="修改文章" width="50%">
          <el-form :model="articleDetailForm" ref="detailFormRef" label-width="120px">
            <el-form-item label="标题">
              <el-input v-model="articleUptateForm.title"></el-input>
            </el-form-item>
            <el-form-item label="内容">
              <el-input type="textarea" v-model="articleUptateForm.content" :rows="30"></el-input>
            </el-form-item>
          </el-form>
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="dialogUpdateVisible = false">取消</el-button>
          </el-form-item>
        </el-dialog>


        <!-- 添加文章表单 -->
        <el-dialog :append-to-body="true" v-model="dialogAddVisible" title="添加文章" width="50%">
          <el-form :model="articleAddForm" ref="detailFormRef" label-width="120px">
            <el-form-item label="标题">
              <el-input v-model="articleAddForm.title"></el-input>
            </el-form-item>
            <el-form-item label="内容">
              <el-input type="textarea" v-model="articleAddForm.content" :rows="30"></el-input>
            </el-form-item>
          </el-form>
          <el-form-item>
            <el-button type="primary" @click="submitAddForm">提交</el-button>
            <el-button @click="dialogAddVisible = false">取消</el-button>
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
