<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const router = useRouter();
const route = useRoute();

const formDataRef = ref({
    address: '',
    area: '',
    zipCode: ''
});

onMounted(() => {
    const id = route.params.id;
    if (id) {
        fetchCultivationLocationData(id);
    }
});

const submitUrl = ref([`${backendEnvVar}/api/cultivation-location/new`]);
const authRef = ref(true);
const submitMethod = ref('POST');

const { performRequest: submitForm } = useRemoteData(submitUrl, authRef, submitMethod, formDataRef);

const onFormSubmit = async (e) => {
    e.preventDefault();
    if (route.params.id) {
        submitUrl.value = [`${backendEnvVar}/api/cultivation-location/${route.params.id}`];
        submitMethod.value = 'PUT';
    }
    await submitForm();
    router.push({ name: 'cultivation-locations' });
};

const fetchCultivationLocationData = async (id) => {
    const url = `${backendEnvVar}/api/cultivation-location/${id}`;
    const fetchUrl = ref([url]);
    const { data, performRequest: fetchData } = useRemoteData(fetchUrl, authRef);
    await fetchData();
    formDataRef.value.address = data.value[url].address;
    formDataRef.value.area = data.value[url].area;
    formDataRef.value.zipCode = data.value[url].zipCode;
};

watch(route, () => {
    const id = route.params.id;
    if (id) {
        fetchCultivationLocationData(id);
    }
});
</script>

<template>
    <div class="container">
        <div class="create">
            <h2 v-if="route.params.id">Edit Cultivation Location</h2>
            <h2 v-else>Add Cultivation Location</h2>
            <div class="content">
                <form class="form" @submit="onFormSubmit">
                    <div class="inputBox">
                        <label for="address">Address:</label>
                        <input
                            v-model="formDataRef.address"
                            id="address"
                            class="short-border"
                            type="text"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label for="area">Area:</label>
                        <input
                            v-model="formDataRef.area"
                            id="area"
                            class="short-border"
                            type="text"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label for="zipCode">Zip Code:</label>
                        <input
                            v-model="formDataRef.zipCode"
                            id="zipCode"
                            class="short-border"
                            type="text"
                            required
                        />
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
