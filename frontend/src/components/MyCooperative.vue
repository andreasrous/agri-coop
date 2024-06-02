<script setup>
import { ref, getCurrentInstance } from 'vue';
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';

defineProps(['name', 'status', 'notes']);

const backendEnvVar = import.meta.env.VITE_BACKEND;
const cooperativeId = getCurrentInstance().vnode.key;
const applicationStore = useApplicationStore();
const { isAuthorized } = applicationStore;

const urlsRef = ref([`${backendEnvVar}/api/cooperative/${cooperativeId}`]);
const authRef = ref(true);
const methodRef = ref('DELETE');
const { performRequest } = useRemoteData(urlsRef, authRef, methodRef);

const emit = getCurrentInstance().emit;

const onFormSubmit = async () => {
    await performRequest();
    emit('cooperativeDeleted');
};
</script>

<template>
    <div class="card">
        <h4>{{ name }}</h4>
        <p><strong>Status: </strong>{{ status }}</p>
        <p><strong>Notes: </strong>{{ notes }}</p>
        <div v-if="isAuthorized('ROLE_USER') || isAuthorized('ROLE_ADMIN')" class="icons">
            <router-link :to="{ name: 'cooperative-edit', params: { id: cooperativeId } }">
                <i class="bx bx-edit-alt"></i>
            </router-link>
            <button @click="onFormSubmit">
                <i class="bx bx-trash"></i>
            </button>
        </div>
        <router-link
            v-if="isAuthorized('ROLE_EMPLOYEE') || isAuthorized('ROLE_ADMIN')"
            :to="{ name: 'cooperative-check', params: { id: cooperativeId } }"
            >Validation Check</router-link
        >
        <div class="rect"></div>
    </div>
</template>

<style scoped>
@import '../assets/card.css';
</style>
