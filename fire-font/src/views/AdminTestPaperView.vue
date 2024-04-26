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
    getTestPaperlist()
})
const indexMethod = (index: number) => {
    return index + 1
}
//获取试卷列表
const testPaperlist = ref([])
const getTestPaperlist = async () => {
    try {
        const res = await request.get('http://localhost:8080/testPaper/getAllTestPaper')
        testPaperlist.value = res.data.data
    } catch (error) {
        console.log(error)
    }
}
//添加试卷
const dialogAddVisible = ref(false)
const addTestPaper = () => {
    dialogAddVisible.value = true
}

const testPaperAddForm = reactive({
    testpaperName: '',
    totalscore: ''
})

function resetAddForm() {
    testPaperAddForm.testpaperName = '';
    testPaperAddForm.totalscore = '';
}

const submitAddForm = async () => {
    try {
        const res = await request.post('http://localhost:8080/testPaper/addTestPaper', testPaperAddForm)
        if (res.data.code === 1) {
            alert('添加试卷成功')
            dialogAddVisible.value = false
            resetAddForm()
            getTestPaperlist()

        } else {
            alert('添加试卷失败')
        }
    } catch (error) {
        console.log(error)
        alert('添加试卷失败')
    }
}
//查看详情
const dialogDetailVisible = ref(false)
const showTestPaperDetail = (testpaperId: number) => {
    getTestPaperAndQuestion(testpaperId)
    dialogDetailVisible.value = true
}
const testPaperDetailForm = reactive({
    testpaperName: '',
    totalscore: '',
    questionList: []
})
const getTestPaperAndQuestion = async (testpaperId: number) => {
    try {
        const res = await request.get('http://localhost:8080/testPaper/getTestPaperQuestions', {
            params: {
                testpaperId: testpaperId
            }
        })
        testPaperDetailForm.testpaperName = res.data.data.testpaperName
        testPaperDetailForm.totalscore = res.data.data.totalscore
        testPaperDetailForm.questionList = res.data.data.questionList
    } catch (error) {
        console.log(error)
    }
}
//修改试卷
const updateTestPaper = (testpaperId: number) => {
    getPaperTestDetail(testpaperId)
    dialogUpdateVisible.value = true
}
const testPaperUpdateForm = reactive({
    testpaperId: null,
    testpaperName: '',
    totalscore: ''
})
const dialogUpdateVisible = ref(false)

const getPaperTestDetail = async (testpaperId: number) => {
    try {
        const res = await request.get('http://localhost:8080/testPaper/getOneTestPaper', {
            params: {
                testpaperId: testpaperId
            }
        })
        testPaperUpdateForm.testpaperId = res.data.data.testpaperId
        testPaperUpdateForm.totalscore = res.data.data.totalscore
        testPaperUpdateForm.testpaperName = res.data.data.testpaperName
    } catch (error) {
        console.log(error)
    }

}
const updateArticle = (articleId: number) => {
    getPaperTestDetail(articleId)
    dialogUpdateVisible.value = true
}

const submitForm = async () => {
    try {
        const res = await request.put('http://localhost:8080/testPaper/updateTestPaper', testPaperUpdateForm)
        if (res.data.code === 1) {
            alert('更新试卷成功')
            dialogUpdateVisible.value = false
            getTestPaperlist()
        } else {
            alert('更新文章失败')
        }
    } catch (error) {
        console.log(error)
        alert('更新文章失败')
    }
}


//删除试卷
const deleteTestPaper = async (testpaperId: number) => {
    try {
        const res = await request.delete('http://localhost:8080/testPaper/deleteTestPaper', {
            params: {
                testpaperId: testpaperId
            }
        })
        if (res.data.code === 1) {
            getTestPaperlist()
        } else {
            alert('删除试卷失败')
        }
    } catch (error) {
        console.log(error)
        alert('删除试卷失败')
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
                <el-button @click="addTestPaper">添加试卷</el-button>
                <el-table :data="testPaperlist" style="width: 100%">
                    <el-table-column type="index" :index="indexMethod" label="序号" width="200" />
                    <el-table-column prop="testpaperId" label="ID" width="260" />
                    <el-table-column prop="testpaperName" label="试卷名" width="360" />
                    <el-table-column prop="totalscore" label="总分" width="470" />
                    <el-table-column prop="createtime" label="创建时间" width="470" />
                    <el-table-column label="Operations" width="120">
                        <template #default="{ row }">
                            <el-button link type="primary" size="small"
                                @click="showTestPaperDetail(row.testpaperId)">查看详情</el-button>
                            <el-button link type="primary" size="small"
                                @click="updateTestPaper(row.testpaperId)">修改</el-button>
                            <el-button link type="primary" size="small"
                                @click="deleteTestPaper(row.testpaperId)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 添加试卷表单 -->
                <el-dialog :append-to-body="true" v-model="dialogAddVisible" title="添加文章" width="50%">
                    <el-form :model="testPaperAddForm" ref="detailFormRef" label-width="120px">
                        <el-form-item label="试卷名">
                            <el-input v-model="testPaperAddForm.testpaperName"></el-input>
                        </el-form-item>
                        <el-form-item label="总分">
                            <el-input v-model="testPaperAddForm.totalscore"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-form-item>
                        <el-button type="primary" @click="submitAddForm">提交</el-button>
                        <el-button @click="dialogAddVisible = false">取消</el-button>
                    </el-form-item>
                </el-dialog>
                <!-- 修改试题表单 -->
                <el-dialog :append-to-body="true" v-model="dialogUpdateVisible" title="修改试题" width="50%">
                    <el-form :model="testPaperUpdateForm" ref="detailFormRef" label-width="120px">
                        <el-form-item label="试卷名">
                            <el-input v-model="testPaperUpdateForm.testpaperName"></el-input>
                        </el-form-item>
                        <el-form-item label="总分">
                            <el-input v-model="testPaperUpdateForm.totalscore"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm">提交</el-button>
                        <el-button @click="dialogUpdateVisible = false">取消</el-button>
                    </el-form-item>
                </el-dialog>
                <!-- 查看详细表单 -->
                <el-dialog :append-to-body="true" v-model="dialogDetailVisible" title="试卷详情" width="50%">
                    <el-form>
                        <el-form-item label="" v-for="(question, i) in testPaperDetailForm.questionList" :key="i">
                            <P style="margin-top: 10px;margin-bottom: 60px;">{{ i + 1 }}.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ question.content }}</P>
                            <el-radio-group style="margin-left: -44px;"    v-model="question.correctAnswer" disabled>
                                <el-radio label="A">A.{{ question.optionA }}</el-radio><br>
                                <el-radio label="B">B.{{ question.optionB }}</el-radio><br>
                                <el-radio label="C">C.{{ question.optionC }}</el-radio><br>
                                <el-radio label="D">D.{{ question.optionD }}</el-radio>
                            </el-radio-group>
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