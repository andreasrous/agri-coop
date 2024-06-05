<script setup>
import { ref, onMounted, computed } from 'vue';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';
import MyCultivationLocation from '../components/MyCultivationLocation.vue';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const urlsRef = ref([]);
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlsRef, authRef);

const { isAuthorized } = useApplicationStore();

const fetchData = () => {
    urlsRef.value = [`${backendEnvVar}/api/cultivation-location`];
    performRequest();
};

onMounted(fetchData);

const handleLocationDeleted = () => {
    fetchData();
};

const cultivationLocationData = computed(() => {
    return data.value[`${backendEnvVar}/api/cultivation-location`] || [];
});
</script>

<template>
    <div class="container">
        <div class="content">
            <div class="loader" v-if="loading">
                <span class="visually-hidden">Loading...</span>
            </div>
            <div v-else>
                <h1>Cultivation Locations</h1>
                <div v-if="cultivationLocationData">
                    <MyCultivationLocation
                        v-for="location in cultivationLocationData"
                        :key="location.id"
                        :address="location.address"
                        :area="location.area"
                        :zipCode="location.zipCode"
                        @locationDeleted="handleLocationDeleted"
                    />
                </div>
                <router-link
                    v-if="isAuthorized('ROLE_USER') || isAuthorized('ROLE_ADMIN')"
                    :to="{ name: 'cultivation-location-new' }"
                    >Add Cultivation Location</router-link
                >
            </div>
        </div>
    </div>
</template>

<style scoped>
@import '../assets/page.css';
</style>
