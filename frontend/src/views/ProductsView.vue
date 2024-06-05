<script setup>
import { ref, onMounted, computed } from 'vue';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';
import MyProduct from '../components/MyProduct.vue';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const urlsRef = ref([]);
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlsRef, authRef);

const { isAuthorized } = useApplicationStore();

const fetchData = () => {
    urlsRef.value = [`${backendEnvVar}/api/product`];
    performRequest();
};

onMounted(fetchData);

const handleProductDeleted = () => {
    fetchData();
};

const productData = computed(() => {
    return data.value[`${backendEnvVar}/api/product`] || [];
});
</script>

<template>
    <div class="container">
        <div class="content">
            <div class="loader" v-if="loading">
                <span class="visually-hidden">Loading...</span>
            </div>
            <div v-else>
                <h1>Products</h1>
                <div v-if="productData">
                    <MyProduct
                        v-for="product in productData"
                        :key="product.id"
                        :name="product.name"
                        :category="product.category"
                        :price="product.price"
                        @productDeleted="handleProductDeleted"
                    />
                </div>
                <router-link
                    v-if="isAuthorized('ROLE_USER') || isAuthorized('ROLE_ADMIN')"
                    :to="{ name: 'product-new' }"
                    >Add Product</router-link
                >
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/page.css';
</style>
