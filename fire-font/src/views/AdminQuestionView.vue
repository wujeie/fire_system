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
  getQuestionList()
})


//获取试题列表
const questionList = ref([])
const getQuestionList = async () => {
  try {
    const res = await request.get('http://localhost:8080/question/getQuestionList')
    questionList.value = res.data.data
  } catch (error) {
    console.log(error)
  }
}
////分配试题
const showAssignDialog = ref(false)
interface paper {
  testpaperId: number,
  testpaperName: string,
  createTime: string,
  totalscore: string;
}
//已分配的试卷ID
const assignedPaperIds = ref<number[]>([])
const getAssignedPaperIds = async (questionId: number) => {
  try {
    const res = await request.get('http://localhost:8080/question/getPaperIdsByQuestionId', {
      params: {
        questionId: questionId
      }
    })
    assignedPaperIds.value = res.data.data
  } catch (error) {
    console.log(error)
  }
}
// 存储试卷数据的数组
const papers = ref<paper[]>([])
const assignQuestionId = ref(0);
const assign = (questionId: number) => {
  assignQuestionId.value = questionId
  getPapers()
  getAssignedPaperIds(assignQuestionId.value)
  showAssignDialog.value = true
}

const getPapers = async () => {
  try {
    const res = await request.get('http://localhost:8080/question/getAllPapers')
    papers.value = res.data.data
  } catch (error) {
    console.log(error)
  }
}

const assignForm = ref<{
  testpaperIds: number[]; // 明确指定 testpaperIds 为 number 类型的数组  
}>({
  testpaperIds: [] // 初始化为一个空数组  
});

//判断是否选中
// const isPaperAssigned = (paperId: number) => {
//   console.log('assignedPaperIds:' + assignedPaperIds.value)
//   return assignedPaperIds.value.includes(paperId);
// };
//提交表单
const submitForm = async () => {

  // 构建提交的数据  
  const submitData = {
    testpaperIds: assignedPaperIds.value,
    questionId: assignQuestionId.value
  };

  try {
    // 发送请求到后端API  
    await request.put('http://localhost:8080/question/assignQuestion', submitData);
    alert('分配成功！');
    showAssignDialog.value = false; // 关闭对话框  
  } catch (error) {
    console.error('Error assigning papers:', error);
    alert('分配试卷失败，请重试！');
  }
};
//表单选中状态改变
const handleCheckedChange = () => {
  assignForm.value.testpaperIds = assignedPaperIds.value;
};

//删除试题
const deleteQuestion = async (questionId: number) => {
  await request.delete('http://localhost:8080/question/deleteQuestion', {
    params: {
      questionId: questionId
    }
  })
  //刷新试题列表
  getQuestionList()
}
//添加试题
const showAddDialog = ref(false)
const addQuestion = () => {
  showAddDialog.value = true



}
const questionForm = reactive({  
      content: '',  
      options: {  
        A: '',  
        B: '',  
        C: '',  
        D: '',  
      },  
      correctAnswer: '',  
    });

const handleClose = () => {
  showAddDialog.value = false;
  resetForm();
}
const resetForm = () => {
  questionForm.content = '';
  questionForm.options = {
    A: '',
    B: '',
    C: '',
    D: '',
  };
  questionForm.correctAnswer = '';
};
//提交
const handleSubmit = async () => {
  try {
    await request.post('http://localhost:8080/question/addQuestion', questionForm);
    alert('添加成功！');
    showAddDialog.value = false;
    getQuestionList();
  } catch (error) {
    console.error('Error adding question:', error);
    alert('添加试题失败，请重试！');
  }
};
</script>


<template>
  <el-container style="min-height: 100vh; min-width: 100vw; overflow: hidden;">
    <!-- 根据roleName动态渲染AsideUser或AsideAdmin -->
    <component :is="roleName === 'user' ? AsideUser : AsideAdmin" :collapse="asideSettings.isCollapse"
      :width="asideSettings.width"></component>
    <el-container style="height: 100vh; width: 100%; display: flex; flex-direction: column;">
      <Header :isCollapse="asideSettings.isCollapse" @changeAside="changeAside"></Header>
      <el-main>
        <el-button @click="addQuestion">添加试题</el-button>
        <el-table :data="questionList" style="width: 100%">
          <el-table-column type="index" label="序号" width="100"></el-table-column>
          <el-table-column prop="questionId" label="试题ID" width="120"></el-table-column>
          <el-table-column prop="content" label="试题内容" width="750"></el-table-column>
          <el-table-column label="选项A" width="150">
            <template #default="scope">
              {{ scope.row.optionA }}
            </template>
          </el-table-column>
          <el-table-column label="选项B" width="150">
            <template #default="scope">
              {{ scope.row.optionB }}
            </template>
          </el-table-column>
          <el-table-column label="选项C" width="150">
            <template #default="scope">
              {{ scope.row.optionC }}
            </template>
          </el-table-column>
          <el-table-column label="选项D" width="150">
            <template #default="scope">
              {{ scope.row.optionD }}
            </template>
          </el-table-column>
          <el-table-column prop="correctAnswer" label="正确答案" width="260"></el-table-column>
          <el-table-column label="Operations" width="120">
            <template #default="{ row }">
              <el-button link type="primary" size="small" @click="assign(row.questionId)">分配</el-button>
              <el-button link type="primary" size="small" @click="deleteQuestion(row.questionId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分配试题的表单 -->
        <el-dialog :append-to-body="true" v-model="showAssignDialog" title="分配试卷">
          <el-checkbox-group v-model="assignedPaperIds" @change="handleCheckedChange">
            <el-checkbox v-for="paper in papers" :label="paper.testpaperId" :key="paper.testpaperId">
              {{ paper.testpaperName }}
            </el-checkbox>
          </el-checkbox-group>
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
          </el-form-item>
        </el-dialog>
        <!-- 添加试题的表单 -->
        <el-dialog :append-to-body="true" v-model="showAddDialog" width="50%" @close="handleClose">
          <el-form :model="questionForm"  label-width="120px">
            <el-form-item label="试题内容">
              <el-input v-model="questionForm.content"></el-input>
            </el-form-item>
            <el-form-item label="选项A">
              <el-input v-model="questionForm.options.A"></el-input>
            </el-form-item>
            <el-form-item label="选项B">
              <el-input v-model="questionForm.options.B"></el-input>
            </el-form-item>
            <el-form-item label="选项C">
              <el-input v-model="questionForm.options.C"></el-input>
            </el-form-item>
            <el-form-item label="选项D">
              <el-input v-model="questionForm.options.D"></el-input>
            </el-form-item>
            <el-form-item label="正确答案">
              <el-select v-model="questionForm.correctAnswer" placeholder="请选择">
                <el-option v-for="(option, key) in questionForm.options" :key="key" :label="key"
                  :value="key"></el-option>
              </el-select>
            </el-form-item>
            <span slot="footer" class="dialog-footer">
              <el-button @click="handleClose">取 消</el-button>
              <el-button type="primary" @click="handleSubmit">确 定</el-button>
            </span>
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