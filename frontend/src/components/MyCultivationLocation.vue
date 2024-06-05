<script setup>
import { ref, getCurrentInstance } from 'vue';
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';

defineProps(['address', 'area', 'zipCode']);

const backendEnvVar = import.meta.env.VITE_BACKEND;
const locationId = getCurrentInstance().vnode.key;
const applicationStore = useApplicationStore();
const { isAuthorized } = applicationStore;

const urlsRef = ref([`${backendEnvVar}/api/cultivation-location/${locationId}`]);
const authRef = ref(true);
const methodRef = ref('DELETE');
const { performRequest } = useRemoteData(urlsRef, authRef, methodRef);

const emit = getCurrentInstance().emit;

const onDelete = async () => {
    await performRequest();
    emit('cultivationLocationDeleted');
};
</script>

<template>
    <div class="card">
        <h4>{{ area }}</h4>
        <p><strong>Address: </strong>{{ address }}</p>
        <p><strong>Zip Code: </strong>{{ zipCode }}</p>
        <div v-if="isAuthorized('ROLE_USER') || isAuthorized('ROLE_ADMIN')" class="icons">
            <router-link :to="{ name: 'cultivation-location-edit', params: { id: locationId } }">
                <i class="bx bx-edit-alt"></i>
            </router-link>
            <button @click="onDelete">
                <i class="bx bx-trash"></i>
            </button>
        </div>
        <div class="rect"></div>
    </div>
</template>

<style scoped>
@import '../assets/card.css';
</style>
