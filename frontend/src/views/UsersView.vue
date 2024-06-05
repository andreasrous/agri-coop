<script setup>
import { ref, onMounted, computed } from 'vue';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';
import MyUser from '../components/MyUser.vue';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const urlsRef = ref([]);
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlsRef, authRef);

const { isAuthorized } = useApplicationStore();

const fetchData = () => {
    urlsRef.value = [`${backendEnvVar}/api/user`];
    performRequest();
};

onMounted(fetchData);

const handleUserDeleted = () => {
    fetchData();
};

const userData = computed(() => {
    return data.value[`${backendEnvVar}/api/user`] || [];
});
</script>

<template>
    <div class="container">
        <div class="content">
            <div class="loader" v-if="loading">
                <span class="visually-hidden">Loading...</span>
            </div>
            <div v-else>
                <h1>Users</h1>
                <div v-if="userData">
                    <MyUser
                        v-for="user in userData"
                        :key="user.id"
                        :username="user.username"
                        :email="user.email"
                        :roles="user.roles"
                        @userDeleted="handleUserDeleted"
                    />
                </div>
                <router-link v-if="isAuthorized('ROLE_ADMIN')" :to="{ name: 'user-new' }"
                    >Add User</router-link
                >
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/page.css';
</style>
