<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';

const router = useRouter();
const applicationStore = useApplicationStore();

const loading = ref(false);

const logout = () => {
  // Perform a logout by flushing user data stored in tab state (pinia) and local storage (browser).
  // REMEMBER: authentication is stateless.
  // That is, if users store a valid JWT they can use it until is expired.
  // We cannot actually perform a logout because JWT cannot be invalided.
  // A solution is to blacklist the JWT until is expired.
  loading.value = true;
  applicationStore.clearUserData();
  setTimeout(function () { }, 2000); // Simulate a remote request.
  router.push({ name: 'login' });
};

function openMenu() {
  const header = document.querySelector('header');
  header.classList.toggle('open');
}
</script>

<template>
  <header>
    <div class="logo">
      <img src="../assets/logo.svg">
      <a href="/"><span>agricoop</span></a>
    </div>
    <nav>
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
      </ul>
    </nav>
    <div class="auth">
      <router-link v-if="applicationStore.isAuthenticated === false" :to="{ name: 'login' }">Login</router-link>
      <button v-if="applicationStore.isAuthenticated === true" @click="logout">Logout</button>
    </div>
    <button id="menuButton" @click="openMenu">
      <i class="bx bx-menu"></i>
    </button>
  </header>
</template>

<style scoped>
@import '../assets/header.css';
</style>