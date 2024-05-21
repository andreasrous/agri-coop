<script setup>
defineProps(['name', 'status', 'notes']);
</script>

<template>
  <div class="card">
    <h4>{{ name }}</h4>
    <p><strong>Status: </strong>{{ status }}</p>
    <p><strong>Notes: </strong>{{ notes }}</p>
    <div class="icons">
      <i class="bx bx-edit-alt"></i>
      <i class="bx bx-trash"></i>
    </div>
    <router-link :to="{ name: 'home' }">Validation Check</router-link>
    <div class="rect"></div>
  </div>
</template>

<style scoped>
@import '../assets/card.css';
</style>