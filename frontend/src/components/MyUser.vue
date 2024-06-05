<script setup>
import { ref, computed, getCurrentInstance } from 'vue';
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';

const props = defineProps(['username', 'email', 'roles']);

const backendEnvVar = import.meta.env.VITE_BACKEND;
const userId = getCurrentInstance().vnode.key;
const applicationStore = useApplicationStore();
const { isAuthorized } = applicationStore;

const urlsRef = ref([`${backendEnvVar}/api/user/${userId}`]);
const authRef = ref(true);
const methodRef = ref('DELETE');
const { performRequest } = useRemoteData(urlsRef, authRef, methodRef);

const emit = getCurrentInstance().emit;

const roleOrder = ['ADMIN', 'USER', 'EMPLOYEE'];

// const capitalize = (str) => str.charAt(0).toUpperCase() + str.slice(1);

const formattedRoles = computed(() => {
    const roles = props.roles.map((role) => role.name.replace('ROLE_', ''));
    const sortedRoles = roles.sort((a, b) => roleOrder.indexOf(a) - roleOrder.indexOf(b));
    return sortedRoles.join(', ');
});

const onDelete = async () => {
    await performRequest();
    emit('userDeleted');
};
</script>

<template>
    <div class="card">
        <h4>{{ username }}</h4>
        <p><strong>Email: </strong>{{ email }}</p>
        <p><strong>Roles: </strong>{{ formattedRoles }}</p>
        <div v-if="isAuthorized('ROLE_ADMIN')" class="icons">
            <router-link :to="{ name: 'user-edit', params: { id: userId } }">
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
