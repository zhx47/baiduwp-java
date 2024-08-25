import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Framework from '../views/Framework.vue';

const routes: RouteRecordRaw[] = [
    {
        path: '/login',
        name: 'Login',
        component: () => import ("@/views/Login.vue")
    },
    {
        path: '/',
        name: 'Framework',
        component: () => import ("@/views/Framework.vue"),
        children: [
            {
                path: '/',
                redirect: "/main/all"
            },
            {
                path: '/main/:category',
                name: '首页',
                meta: {
                    needLogin: true,
                    menuCode: "main"
                },
                component: () => import ("@/views/main/Main.vue")
            },
        ]
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;