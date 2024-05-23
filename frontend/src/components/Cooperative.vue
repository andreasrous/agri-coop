<script setup>
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const { isAuthorized } = useApplicationStore();
defineProps(['name', 'status', 'notes']);
</script>

<template>
  <div class="card">
    <h4>{{ name }}</h4>
    <p><strong>Status: </strong>{{ status }}</p>
    <p><strong>Notes: </strong>{{ notes }}</p>
    <div v-if="isAuthorized('ROLE_USER')" class="icons">
      <i class="bx bx-edit-alt"></i>
      <i class="bx bx-trash"></i>
    </div>
    <router-link v-if="isAuthorized('ROLE_EMPLOYEE')" :to="{ name: 'home' }">Validation Check</router-link>
    <div class="rect"></div>
  </div>
</template>

<style scoped>
@import '../assets/card.css';
</style>