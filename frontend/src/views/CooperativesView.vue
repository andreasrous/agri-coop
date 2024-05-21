<script setup>
import { onMounted, ref } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const urlRef = ref(`${backendEnvVar}/api/cooperative`);
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlRef, authRef);

import Cooperative from '../components/Cooperative.vue';

onMounted(() => {
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
        <router-link :to="{ name: 'home' }">Add Cooperative</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import '../assets/page.css';
</style>