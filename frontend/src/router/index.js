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
            path: '/cooperative',
            name: 'cooperatives',
            component: () => import('../views/CooperativesView.vue'),
            meta: { requiresAuth: true }
        },
        // {
        //     path: '/cooperative/new',
        //     name: 'cooperative-new',
        //     component: () => import('../views/CreateCooperativeView.vue'),
        //     meta: { requiresAuth: true }
        // },
        // {
        //     path: '/cooperative/:id',
        //     name: 'cooperative',
        //     component: () => import('../views/CooperativeView.vue'),
        //     meta: { requiresAuth: true },
        //     children: [
        //         {
        //             path: '',
        //             name: 'cooperative-details',
        //             component: () => import('../views/CooperativeDetailsView.vue'),
        //             meta: { requiresAuth: true }
        //         }
        //     ]
        // },
        // {
        //     path: '/product',
        //     name: 'products',
        //     component: () => import('../views/ProductsView.vue'),
        //     meta: { requiresAuth: true }
        // },
        // {
        //     path: '/product/new',
        //     name: 'product-new',
        //     component: () => import('../views/CreateProductView.vue'),
        //     meta: { requiresAuth: true }
        // },
        // {
        //     path: '/product/:id',
        //     name: 'product',
        //     component: () => import('../views/ProductView.vue'),
        //     meta: { requiresAuth: true },
        //     children: [
        //         {
        //             path: '',
        //             name: 'product-details',
        //             component: () => import('../views/ProductDetailsView.vue'),
        //             meta: { requiresAuth: true }
        //         }
        //     ]
        // },
        // {
        //     path: '/cultivations_location',
        //     name: 'cultivation-locations',
        //     component: () => import('../views/CultivationLocationsView.vue'),
        //     meta: { requiresAuth: true }
        // },
        // {
        //     path: '/cultivation_location/new',
        //     name: 'cultivation-location-new',
        //     component: () => import('../views/CreateCultivationLocationView.vue'),
        //     meta: { requiresAuth: true }
        // },
        // {
        //     path: '/cultivation_location/:id',
        //     name: 'cultivation-location',
        //     component: () => import('../views/CultivationLocationView.vue'),
        //     meta: { requiresAuth: true },
        //     children: [
        //         {
        //             path: '',
        //             name: 'cultivation-location-details',
        //             component: () => import('../views/CultivationLocationDetailsView.vue'),
        //             meta: { requiresAuth: true }
        //         }
        //     ]
        // },
        // {
        //     path: '/user',
        //     name: 'users',
        //     component: () => import('../views/UsersView.vue'),
        //     meta: { requiresAuth: true }
        // },
        // {
        //     path: '/user/new',
        //     name: 'user-new',
        //     component: () => import('../views/CreateUserView.vue'),
        //     meta: { requiresAuth: true }
        // },
        // {
        //     path: '/user/:id',
        //     name: 'user',
        //     component: () => import('../views/UserView.vue'),
        //     meta: { requiresAuth: true },
        //     children: [
        //         {
        //             path: '',
        //             name: 'user-details',
        //             component: () => import('../views/UserDetailsView.vue'),
        //             meta: { requiresAuth: true }
        //         }
        //     ]
        // },
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