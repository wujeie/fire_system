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
//开始答题
const dialogDetailVisible = ref(false)
const answers = ref<string[]>([]);
const showTestPaperDetail = (testpaperId: number) => {
    getTestPaperAndQuestion(testpaperId)
    dialogDetailVisible.value = true
}
const testPaperDetailForm = reactive({
    testpaperId: 0,
    testpaperName: '',
    totalscore: '',
    questionList: [],
})
const getTestPaperAndQuestion = async (testpaperId: number) => {
    try {
        const res = await request.get('http://localhost:8080/testPaper/getTestPaperQuestions', {
            params: {
                testpaperId: testpaperId
            }
        })
        testPaperDetailForm.testpaperId = res.data.data.testpaperId
        testPaperDetailForm.testpaperName = res.data.data.testpaperName
        testPaperDetailForm.totalscore = res.data.data.totalscore
        testPaperDetailForm.questionList = res.data.data.questionList
    } catch (error) {
        console.log(error)
    }
}
//提交答案
const submitAnswers = async ()=>{
    const submitData = {
        testpaperId: testPaperDetailForm.testpaperId,
        answers: answers.value
    }
    const res = await request.post('http://localhost:8080/testPaper/submitAnswers',submitData)
    const msg:string = res.data.msg
    alert(msg)
    dialogDetailVisible.value = false
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
                <el-table :data="testPaperlist" style="width: 100%">
                    <el-table-column type="index" :index="indexMethod" label="序号" width="200" />
                    <el-table-column prop="testpaperId" label="ID" width="260" />
                    <el-table-column prop="testpaperName" label="试卷名" width="360" />
                    <el-table-column prop="totalscore" label="总分" width="470" />
                    <el-table-column prop="createtime" label="创建时间" width="470" />
                    <el-table-column label="Operations" width="120">
                        <template #default="{ row }">
                            <el-button link type="primary" size="small"
                                @click="showTestPaperDetail(row.testpaperId)">开始答题</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-dialog :append-to-body="true" v-model="dialogDetailVisible" title="答题页面" width="50%">
                    <el-form>
                        <el-form-item label="" v-for="(question, i) in testPaperDetailForm.questionList" :key="i">
                            <P style="margin-top: 10px;margin-bottom: 60px;">{{ i + 1
                                }}.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ question.content }}</P>
                            <el-radio-group style="margin-left: -44px;" v-model="answers[i]">
                                <el-radio label="A">A.{{ question.optionA }}</el-radio><br>
                                <el-radio label="B">B.{{ question.optionB }}</el-radio><br>
                                <el-radio label="C">C.{{ question.optionC }}</el-radio><br>
                                <el-radio label="D">D.{{ question.optionD }}</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item>
                                <el-button type="primary" @click="submitAnswers">提交</el-button>
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