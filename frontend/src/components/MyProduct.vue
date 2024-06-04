<script setup>
import { ref, getCurrentInstance } from 'vue';
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';

defineProps(['name', 'category', 'price']);

const backendEnvVar = import.meta.env.VITE_BACKEND;
const productId = getCurrentInstance().vnode.key;
const applicationStore = useApplicationStore();
const { isAuthorized } = applicationStore;

const urlsRef = ref([`${backendEnvVar}/api/product/${productId}`]);
const authRef = ref(true);
const methodRef = ref('DELETE');
const { performRequest } = useRemoteData(urlsRef, authRef, methodRef);

const emit = getCurrentInstance().emit;

const onDelete = async () => {
    await performRequest();
    emit('productDeleted');
};
</script>

<template>
    <div class="card">
        <h4>{{ name }}</h4>
        <p><strong>Category: </strong>{{ category }}</p>
        <p><strong>Price: </strong>{{ price }}</p>
        <div v-if="isAuthorized('ROLE_USER') || isAuthorized('ROLE_ADMIN')" class="icons">
            <router-link :to="{ name: 'product-edit', params: { id: productId } }">
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
