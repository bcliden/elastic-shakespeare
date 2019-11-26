<template>
  <v-container class="pa-0">
    <Title>
      Speakers
      <template v-slot:subtitle>top 100 by lines spoken</template>
    </Title>
    <v-row>
      <v-card
        v-for="(speaker, i) in filteredSpeakers"
        :key="i"
        class="ma-2"
        link
        :to="getUrl(speaker.speaker)"
      >
        <v-card-title>{{ speaker.speaker }}</v-card-title>
        <v-card-subtitle>{{ formatted(speaker.lines) }} lines</v-card-subtitle>
      </v-card>
      <v-progress-circular indeterminate size="50" v-if="loading" class="mx-auto"></v-progress-circular>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import Title from "../components/Title.vue";

export default {
  data: () => ({
    speakers: [],
    filterPlay: "any",
    loading: true
  }),
  methods: {
    formatted(number) {
      return new Number(number).toLocaleString();
    },
    getUrl(speaker) {
      return `speakers/${speaker.toLowerCase()}`;
    }
  },
  computed: {
    filteredSpeakers: function() {
      return this.speakers.filter(speaker => {
        if (this.filterPlay === "any") return true;
        return speaker.play.contains(this.filteredPlay);
      });
    }
  },
  created() {
    this.loading = true;
    axios
      .get("http://localhost:8080/api/speakers")
      .then(({ data }) => {
        this.loading = false;
        this.speakers = data;
      })
      .catch(err => {
        this.loading = false;
        console.error(err);
      });
  },
  components: {
    Title
  }
};
</script>

<style>
</style>