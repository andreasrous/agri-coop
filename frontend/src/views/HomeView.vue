<script setup>
import { ref, onMounted, computed } from 'vue';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const urlsRef = ref([]);
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlsRef, authRef);

const { userData } = useApplicationStore();

const fetchData = () => {
    urlsRef.value = [`${backendEnvVar}/api/user/${userData.id}`];
    performRequest();
};

onMounted(fetchData);

const user = computed(() => {
    return data.value[`${backendEnvVar}/api/user/${userData.id}`] || [];
});

const roleOrder = ['ADMIN', 'USER', 'EMPLOYEE'];

const formattedRoles = computed(() => {
    if (!user.value.roles) {
        return '';
    }
    const roles = user.value.roles.map((role) => role.name.replace('ROLE_', ''));
    const sortedRoles = roles.sort((a, b) => roleOrder.indexOf(a) - roleOrder.indexOf(b));
    return sortedRoles.join(', ');
});
</script>

<template>
    <div class="container">
        <div class="content">
            <div class="loader" v-if="loading">
                <span class="visually-hidden">Loading...</span>
            </div>
            <div v-else>
                <h1>Home</h1>
                <p>
                    Logged in as: <strong>{{ user.username }}</strong>
                </p>
                <p>
                    Roles: <strong>{{ formattedRoles }}</strong>
                </p>
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/page.css';
</style>
