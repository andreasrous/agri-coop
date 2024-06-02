<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { storeToRefs } from 'pinia';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const route = useRoute();
const applicationStore = useApplicationStore();
const { userData } = storeToRefs(applicationStore);

const formDataRef = ref({
    name: '',
    vat: '',
    farmers: [],
    products: [],
    cultivationLocations: []
});
const urlsRef = ref([
    `${backendEnvVar}/api/product`,
    `${backendEnvVar}/api/cultivation-location`,
    `${backendEnvVar}/api/user/farmers`
]);
const authRef = ref(true);
const methodRef = ref('GET');

const { data, error, loading, performRequest } = useRemoteData(urlsRef, authRef, methodRef);

onMounted(() => {
    performRequest();
    const id = route.params.id;
    if (id) {
        fetchCooperativeData(id);
    }
});

const products = computed(() => data.value[`${backendEnvVar}/api/product`] || []);
const farmers = computed(() => data.value[`${backendEnvVar}/api/user/farmers`] || []);
const cultivationLocations = computed(
    () => data.value[`${backendEnvVar}/api/cultivation-location`] || []
);

const selectedFarmers = ref([]);
const selectedProducts = ref([]);
const selectedLocations = ref([]);

const submitUrl = ref([`${backendEnvVar}/api/cooperative/new`]);
const submitMethod = ref('POST');

const { performRequest: submitForm } = useRemoteData(submitUrl, authRef, submitMethod, formDataRef);

const onFormSubmit = async () => {
    formDataRef.value.farmers = selectedFarmers.value.map((id) => ({ id }));
    formDataRef.value.products = selectedProducts.value.map((id) => ({ id }));
    formDataRef.value.cultivationLocations = selectedLocations.value.map((id) => ({ id }));

    if (route.params.id) {
        submitUrl.value = [`${backendEnvVar}/api/cooperative/${route.params.id}`];
        submitMethod.value = 'PUT';
    }

    await submitForm();
    router.push({ name: 'cooperatives', params: { id: userData.value.id } });
};

const fetchCooperativeData = async (id) => {
    const url = `${backendEnvVar}/api/cooperative/${id}`;
    const fetchUrl = ref([url]);
    const { data, performRequest: fetchData } = useRemoteData(fetchUrl, authRef);
    await fetchData();
    formDataRef.value.name = data.value[url].name;
    formDataRef.value.vat = data.value[url].vat;
    selectedFarmers.value = data.value[url].farmers.map((farmer) => farmer.id);
    selectedProducts.value = data.value[url].products.map((product) => product.id);
    selectedLocations.value = data.value[url].cultivationLocations.map((location) => location.id);
};

watch(route, () => {
    const id = route.params.id;
    if (id) {
        fetchCooperativeData(id);
    }
});
</script>

<template>
    <div class="container">
        <div class="create">
            <h2 v-if="route.params.id">Edit Cooperative</h2>
            <h2 v-else>Create Cooperative</h2>
            <div class="content">
                <div class="loader" v-if="loading">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <form class="form" v-else>
                    <div v-if="error">{{ error.message }}</div>
                    <div class="inputBox">
                        <label for="name">Name:</label>
                        <input
                            v-model="formDataRef.name"
                            id="name"
                            class="short-border"
                            type="text"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label for="vat">VAT:</label>
                        <input
                            v-model="formDataRef.vat"
                            id="vat"
                            class="short-border"
                            type="text"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label>Farmers:</label>
                        <div class="checkbox-container">
                            <div v-for="farmer in farmers" :key="farmer.id">
                                <label>
                                    <input
                                        type="checkbox"
                                        :value="farmer.id"
                                        v-model="selectedFarmers"
                                    />
                                    <span class="label-text">{{ farmer.username }}</span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="inputBox">
                        <label>Products:</label>
                        <div class="checkbox-container">
                            <div v-for="product in products" :key="product.id">
                                <label>
                                    <input
                                        type="checkbox"
                                        :value="product.id"
                                        v-model="selectedProducts"
                                    />
                                    <span class="label-text">{{ product.name }}</span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="inputBox">
                        <label>Cultivation Locations:</label>
                        <div class="checkbox-container">
                            <div v-for="location in cultivationLocations" :key="location.id">
                                <label>
                                    <input
                                        type="checkbox"
                                        :value="location.id"
                                        v-model="selectedLocations"
                                    />
                                    <span class="label-text">{{ location.area }}</span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="inputBox">
                        <input
                            @click="onFormSubmit"
                            class="short-border"
                            type="button"
                            value="Save"
                        />
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/form.css';
</style>
