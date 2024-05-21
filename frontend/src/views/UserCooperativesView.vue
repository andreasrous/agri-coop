<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import { useRemoteData } from '@/composables/useRemoteData.js';
import Cooperative from '../components/Cooperative.vue';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const route = useRoute();

const userIdRef = ref(null);
const urlRef = computed(() => {
  return `${backendEnvVar}/api/cooperative/user/${userIdRef.value}`;
});
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlRef, authRef);

const { isAuthorized } = useApplicationStore();

onMounted(() => {
  userIdRef.value = route.params.id;
  performRequest();
});
</script>

<template>
  <div class="container">
    <div class="content">
      <div class="loader" v-if="loading">
        <span class="visually-hidden">Loading...</span>
      </div>
      <div v-else>
        <h1>Cooperatives</h1>
        <div v-if="data">
          <Cooperative v-for="cooperative in data" :key="cooperative.id" :name="cooperative.name"
            :status="cooperative.status" :notes="cooperative.notes" />
        </div>
        <router-link v-if="isAuthorized('ROLE_USER')" :to="{ name: 'home' }">Add Cooperative</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import '../assets/page.css';
</style>