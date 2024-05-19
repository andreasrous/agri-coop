<script setup>
import { onMounted, ref } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const urlRef = ref(`${backendEnvVar}/api/cooperative`);
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlRef, authRef);

import CooperativeDetails from '../components/CooperativeDetails.vue';

onMounted(() => {
  performRequest();
});
</script>

<template>
  <div>
    <h1>Cooperatives</h1>
    <div v-if="data">
      <CooperativeDetails v-for="cooperative in data" :key="cooperative.id" :name="cooperative.name"
        :status="cooperative.status" :notes="cooperative.notes" />
    </div>
  </div>
</template>