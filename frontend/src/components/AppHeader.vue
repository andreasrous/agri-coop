<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { storeToRefs } from 'pinia';

const router = useRouter();
const applicationStore = useApplicationStore();
const { userData, isAuthenticated } = storeToRefs(applicationStore);
const { clearUserData, isAuthorized } = applicationStore;
const loading = ref(false);

const logout = () => {
    loading.value = true;
    clearUserData();
    setTimeout(function () {}, 2000);
    router.push({ name: 'login' });
};

function openMenu() {
    const header = document.querySelector('header');
    header.classList.toggle('open');
}
</script>

<template>
    <header>
        <div class="logo">
            <img src="../assets/logo.svg" />
            <router-link :to="{ name: 'home' }"><span>agricoop</span></router-link>
        </div>
        <nav>
            <ul>
                <li v-if="isAuthenticated">
                    <router-link :to="{ name: 'home' }">Home</router-link>
                </li>
                <li v-if="isAuthenticated && isAuthorized('ROLE_ADMIN')">
                    <router-link :to="{ name: 'users' }">Users</router-link>
                </li>
                <li v-if="isAuthenticated">
                    <router-link :to="{ name: 'cooperatives', params: { id: userData.id } }"
                        >Cooperatives</router-link
                    >
                </li>
                <li v-if="isAuthenticated === true">
                    <router-link :to="{ name: 'products' }">Products</router-link>
                </li>
                <li v-if="isAuthenticated === true">
                    <router-link :to="{ name: 'cultivation-locations' }"
                        >Cultivation Locations</router-link
                    >
                </li>
            </ul>
        </nav>
        <div class="auth">
            <router-link v-if="!isAuthenticated" :to="{ name: 'login' }">Login</router-link>
            <button v-if="isAuthenticated" @click="logout">Logout</button>
        </div>
        <button id="menuButton" @click="openMenu">
            <i class="bx bx-menu"></i>
        </button>
    </header>
</template>

<style scoped>
@import '../assets/header.css';
</style>
