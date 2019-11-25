<template>
  <v-container>
      <Title>
        Home
        <template v-slot:subtitle>Your Shakespeare API</template>
      </Title>

  </v-container>
</template>

<script>
import Title from '../components/Title.vue';

export default {
  name: "home",
  components: {
    Title
  }
};
</script>
