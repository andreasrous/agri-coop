<script setup>
import { useApplicationStore } from '@/stores/application.js';
const { userData } = useApplicationStore();
</script>

<template>
    <div class="container">
        <div class="content">
            <h1>Home</h1>
            <p>
                Logged in as: <strong>{{ userData.username }}</strong>
            </p>
        </div>
    </div>
</template>
