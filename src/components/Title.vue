<template>
  <v-row class="ma-3">
    <h1 class="inline display-3 mr-5">
      <slot></slot>
    </h1>
    <small class="subtitle-1 align-self-end">
      <slot name="subtitle"></slot>
    </small>
  </v-row>
</template>

<script>
export default {};
</script>

<style scoped>
    .inline {
        display: inline;
    }
</style>