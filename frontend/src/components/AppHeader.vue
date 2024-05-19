<script setup>
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const applicationStore = useApplicationStore();
</script>

<template>
  <header>
    <div>
      <a href="/"><span>agricoop</span></a>
      <ul>
        <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'home' }">Home</router-link>
        </li>
        <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'cooperatives' }">Cooperatives</router-link>
        </li>
        <!-- <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'products' }">Products</router-link>
        </li>
        <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'cultivation-locations' }">Cultivation Locations</router-link>
        </li> -->
        <li v-if="applicationStore.isAuthenticated === false">
          <router-link :to="{ name: 'login' }">Login</router-link>
        </li>
        <li v-if="applicationStore.isAuthenticated === true">
          <router-link :to="{ name: 'logout' }">Logout</router-link>
        </li>
      </ul>
    </div>
  </header>
</template>