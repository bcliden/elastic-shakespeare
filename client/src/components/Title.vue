<template>
  <v-row class="ma-3 align-end">
    <h1 class="inline display-3 mr-5">
      <slot></slot>
    </h1>
    <small class="subtitle-1 mr-3">
      <slot name="subtitle"></slot>
    </small>
    <slot name="sort"></slot>
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