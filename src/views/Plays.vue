<template>
  <v-container class="pa-0">
    <Title>
      Plays
      <template v-slot:subtitle>all 36 sorted by lines spoken</template>
    </Title>
    <v-row>
      <v-card
        v-for="(play, i) in filteredPlays"
        :key="i"
        class="ma-2"
        link
        :to="getUrl(play.play)"
      >
        <v-card-title>{{ play.play }}</v-card-title>
        <v-card-subtitle>{{ formatted(play.lineCount) }} lines</v-card-subtitle>
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
    plays: [],
    filterPlay: "any",
    loading: true
  }),
  methods: {
    formatted(number) {
      return new Number(number).toLocaleString();
    },
    getUrl(play) {
      return `plays/${play.toLowerCase()}`;
    }
  },
  computed: {
    filteredPlays: function() {
      return this.plays.filter(play => {
        if (this.filterPlay === "any") return true;
        return play.contains(this.filteredPlay);
      });
    }
  },
  created() {
    this.loading = true;
    axios
      .get("http://localhost:8080/api/plays")
      .then(({ data }) => {
        this.loading = false;
        this.plays = data;
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