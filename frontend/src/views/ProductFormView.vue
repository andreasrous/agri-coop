<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const router = useRouter();
const route = useRoute();

const formDataRef = ref({
    name: '',
    category: '',
    price: ''
});

onMounted(() => {
    const id = route.params.id;
    if (id) {
        fetchProductData(id);
    }
});

const submitUrl = ref([`${backendEnvVar}/api/product/new`]);
const authRef = ref(true);
const submitMethod = ref('POST');

const { performRequest: submitForm } = useRemoteData(submitUrl, authRef, submitMethod, formDataRef);

const onFormSubmit = async (e) => {
    e.preventDefault();
    if (route.params.id) {
        submitUrl.value = [`${backendEnvVar}/api/product/${route.params.id}`];
        submitMethod.value = 'PUT';
    }
    await submitForm();
    router.push({ name: 'products' });
};

const fetchProductData = async (id) => {
    const url = `${backendEnvVar}/api/product/${id}`;
    const fetchUrl = ref([url]);
    const { data, performRequest: fetchData } = useRemoteData(fetchUrl, authRef);
    await fetchData();
    formDataRef.value.name = data.value[url].name;
    formDataRef.value.category = data.value[url].category;
    formDataRef.value.price = data.value[url].price;
};

watch(route, () => {
    const id = route.params.id;
    if (id) {
        fetchProductData(id);
    }
});
</script>

<template>
    <div class="container">
        <div class="create">
            <h2 v-if="route.params.id">Edit Product</h2>
            <h2 v-else>Add Product</h2>
            <div class="content">
                <form class="form" @submit="onFormSubmit">
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
                        <label for="category">Category:</label>
                        <input
                            v-model="formDataRef.category"
                            id="category"
                            class="short-border"
                            type="text"
                            required
                        />
                    </div>
                    <div class="inputBox">
                        <label for="price">Price:</label>
                        <input
                            v-model="formDataRef.price"
                            id="price"
                            class="short-border"
                            type="number"
                            step="0.01"
                            min="0"
                            max="20"
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
