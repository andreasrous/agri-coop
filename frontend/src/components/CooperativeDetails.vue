<script setup>
defineProps(['name', 'status', 'notes']);
</script>

<template>
  <div class="cooperative-details">
    <h4>{{ name }}</h4>
    <p><strong>Status: </strong>{{ status }}</p>
    <p><strong>Notes: </strong>{{ notes }}</p>
    <span>edit</span>
    <span>delete</span>
  </div>
</template>