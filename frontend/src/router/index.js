import { createRouter, createWebHistory } from 'vue-router';
// import HomeView from '../views/HomeView.vue';
import { useApplicationStore } from '@/stores/application.js';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            // component: HomeView,
            component: () => import('../views/HomeView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/cooperative/user/:id',
            name: 'cooperatives',
            component: () => import('../views/CooperativesView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/cooperative/new',
            name: 'cooperative-new',
            component: () => import('../views/CooperativeFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/cooperative/:id',
            name: 'cooperative-edit',
            component: () => import('../views/CooperativeFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/cooperative/:id/check',
            name: 'cooperative-check',
            component: () => import('../views/CheckCooperativeView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/user',
            name: 'users',
            component: () => import('../views/UsersView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/user/new',
            name: 'user-new',
            component: () => import('../views/UserFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/user/:id',
            name: 'user-edit',
            component: () => import('../views/UserFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/product',
            name: 'products',
            component: () => import('../views/ProductsView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/product/new',
            name: 'product-new',
            component: () => import('../views/ProductFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/product/:id',
            name: 'product-edit',
            component: () => import('../views/ProductFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/cultivations-location',
            name: 'cultivation-locations',
            component: () => import('../views/CultivationLocationsView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/cultivation-location/new',
            name: 'cultivation-location-new',
            component: () => import('../views/CultivationLocationFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/cultivation-location/:id',
            name: 'cultivation-location-edit',
            component: () => import('../views/CultivationLocationFormView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/LoginView.vue')
        },
        {
            path: '/signup',
            name: 'signup',
            component: () => import('../views/SignupView.vue')
        }
    ]
});

router.beforeEach((to, from, next) => {
    const { isAuthenticated } = useApplicationStore();
    const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

    if (requiresAuth && !isAuthenticated) {
        console.log('user not authenticated. redirecting to /login');
        next('/login');
    } else {
        next();
    }
});

export default router;
