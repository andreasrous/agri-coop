<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const router = useRouter();
const route = useRoute();

const formDataRef = ref({
    firstName: '',
    lastName: '',
    vat: '',
    username: '',
    email: '',
    password: '',
    roles: []
});

onMounted(() => {
    const id = route.params.id;
    if (id) {
        fetchUserData(id);
    }
});

const roles = ref([
    {
        id: 1,
        name: 'User'
    },
    {
        id: 2,
        name: 'Employee'
    },
    {
        id: 3,
        name: 'Admin'
    }
]);
const selectedRoles = ref([]);

const submitUrl = ref([`${backendEnvVar}/api/user/new`]);
const authRef = ref(true);
const submitMethod = ref('POST');

const { performRequest: submitForm } = useRemoteData(submitUrl, authRef, submitMethod, formDataRef);

const onFormSubmit = async (e) => {
    e.preventDefault();
    formDataRef.value.roles = selectedRoles.value.map((id) => ({ id }));
    if (route.params.id) {
        submitUrl.value = [`${backendEnvVar}/api/user/${route.params.id}`];
        submitMethod.value = 'PUT';
    }
    await submitForm();
    router.push({ name: 'users' });
};

const fetchUserData = async (id) => {
    const url = `${backendEnvVar}/api/user/${id}`;
    const fetchUrl = ref([url]);
    const { data, performRequest: fetchData } = useRemoteData(fetchUrl, authRef);
    await fetchData();
    formDataRef.value.firstName = data.value[url].firstName;
    formDataRef.value.lastName = data.value[url].lastName;
    formDataRef.value.vat = data.value[url].vat;
    formDataRef.value.username = data.value[url].username;
    formDataRef.value.email = data.value[url].email;
    selectedRoles.value = data.value[url].roles.map((role) => role.id);
};

watch(route, () => {
    const id = route.params.id;
    if (id) {
        fetchUserData(id);
    }
});
</script>

<template>
    <div class="container">
        <div class="create">
            <h2 v-if="route.params.id">Edit User</h2>
            <h2 v-else>Add User</h2>
            <div class="content">
                <form class="form" @submit="onFormSubmit">
                    <div class="inputBox">
                        <label for="firstName">First Name:</label>
                        <input
                            v-model="formDataRef.firstName"
                            id="firstName"
                            class="short-border"
                            type="text"
                        />
                    </div>
                    <div class="inputBox">
                        <label for="lastName">Last Name:</label>
                        <input
                            v-model="formDataRef.lastName"
                            id="lastName"
                            class="short-border"
                            type="text"
                        />
                    </div>
                    <div class="inputBox">
                        <label for="vat">VAT:</label>
                        <input
                            v-model="formDataRef.vat"
                            id="vat"
                            class="short-border"
                            type="text"
                        />
                    </div>
                    <div class="inputBox">
                        <label for="username">Username:</label>
                        <input
                            v-model="formDataRef.username"
                            id="username"
                            class="short-border"
                            type="text"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label for="email">Email:</label>
                        <input
                            v-model="formDataRef.email"
                            id="email"
                            class="short-border"
                            type="email"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label for="password">Password:</label>
                        <input
                            v-model="formDataRef.password"
                            id="password"
                            class="short-border"
                            type="password"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label>Roles:</label>
                        <div class="checkbox-container">
                            <div v-for="role in roles" :key="role.id">
                                <label>
                                    <input
                                        type="checkbox"
                                        :value="role.id"
                                        v-model="selectedRoles"
                                    />
                                    <span class="label-text">{{ role.name }}</span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="inputBox">
                        <input class="short-border" type="submit" value="Save" />
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/form.css';
</style>
