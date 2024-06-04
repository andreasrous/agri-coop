<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';
import MyCooperative from '../components/MyCooperative.vue';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const route = useRoute();

const userIdRef = ref(null);
const urlsRef = ref([]);
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlsRef, authRef);

const { isAuthorized } = useApplicationStore();

const fetchData = () => {
    if (isAuthorized('ROLE_USER')) {
        userIdRef.value = route.params.id;
        urlsRef.value = [`${backendEnvVar}/api/cooperative/user/${userIdRef.value}`];
    }
    if (isAuthorized('ROLE_ADMIN') || isAuthorized('ROLE_EMPLOYEE')) {
        urlsRef.value = [`${backendEnvVar}/api/cooperative`];
    }
    performRequest();
};

onMounted(fetchData);

const handleCooperativeDeleted = () => {
    fetchData();
};

const cooperativeData = computed(() => {
    const cooperativeUrl =
        !isAuthorized('ROLE_ADMIN') && !isAuthorized('ROLE_EMPLOYEE')
            ? `${backendEnvVar}/api/cooperative/user/${userIdRef.value}`
            : `${backendEnvVar}/api/cooperative`;

    return data.value[cooperativeUrl] || [];
});
</script>

<template>
    <div class="container">
        <div class="content">
            <div class="loader" v-if="loading">
                <span class="visually-hidden">Loading...</span>
            </div>
            <div v-else>
                <h1>Cooperatives</h1>
                <div v-if="cooperativeData">
                    <MyCooperative
                        v-for="cooperative in cooperativeData"
                        :key="cooperative.id"
                        :name="cooperative.name"
                        :status="cooperative.status"
                        :notes="cooperative.notes"
                        @cooperativeDeleted="handleCooperativeDeleted"
                    />
                </div>
                <router-link
                    v-if="isAuthorized('ROLE_USER') || isAuthorized('ROLE_ADMIN')"
                    :to="{ name: 'cooperative-new' }"
                    >Add Cooperative</router-link
                >
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/page.css';
</style>
