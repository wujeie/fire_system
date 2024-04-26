import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import Login from "@/views/LoginView.vue";
import AdminUser from "@/views/AdminUserView.vue";
import Home from "@/components/Home.vue";
import Test from "@/components/Test.vue";
import AdminArticle from "@/views/AdminArticleView.vue";
import AdminVideo from "@/views/AdminVideoView.vue";
import AdminQuestion from "@/views/AdminQuestionView.vue"
import AdminTestPaper from "@/views/AdminTestPaperView.vue"
import AdminAuthrity from "@/views/AdminAuthrityView.vue"
import AdminMessage from "@/views/AdminMessageView.vue"
import UserAritcle from "@/views/UserAriticleView.vue"
import UserVideo from "@/views/UserVideoView.vue"
import UserTest from "@/views/UserTestView.vue"
import UserUser from "@/views/UserUserView.vue"
import UserMessage from "@/views/UserMessageView.vue"
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/home",
      name: "home",
      component: HomeView,
    },
    {
      path: "/userHome",
      name: "userHome",
      component: Home,
    },
    {
      path: "/login",
      name: 'Login',
      component: Login,
    },
    {
      path:"/admin/user",
      name: 'AdminUser',
      component: AdminUser,
    },

    {
      path: "/",
      redirect: "/login",
    },
    {
      path:"/test",
      component: Test
    },
    {
      path:"/admin/article",
      component: AdminArticle
    },
    {
      path: "/admin/video",
      component: AdminVideo
    },
    {
      path:"/admin/question",
      component: AdminQuestion
    },
    {
      path:"/admin/testPaper",
      component: AdminTestPaper
    },
    {
      path:"/admin/authrity",
      component: AdminAuthrity
    },
    {
      path:"/admin/message",
      component: AdminMessage
    },
    {
      path:"/user/article",
      component: UserAritcle
    },
    {
      path:"/user/video",
      component: UserVideo
    },
    {
      path:"/user/test",
      component: UserTest
    },
    {
      path:"/user/alter",
      component: UserUser
    },
    {
      path:"/user/message",
      component: UserMessage
    }
  ],
});

export default router;
