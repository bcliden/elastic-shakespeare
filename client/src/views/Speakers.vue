<template>
  <v-container class="pa-0">
    <Title>
      Speakers
      <template v-slot:subtitle>top 100 by</template>
      <template v-slot:sort>
        <v-select
          :items="sorts"
          v-model="selectedSort"
          label="Sort"
          dense
          flat
          hide-details
          single-line
          class="flex-grow-0"
        ></v-select>
      </template>
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
        <v-card-subtitle class="text-center">{{ 
            getText( speaker, selectedSort )
          }}</v-card-subtitle>
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
    selectedSort: "lines",
    loading: true,
    sorts: [
      { text: "lines spoken", value: "lines" },
      { text: "most play appearances", value: "plays" }
    ]
  }),
  methods: {
    formatted(number) {
      return new Number(number).toLocaleString();
    },
    getUrl(speaker) {
      return `speakers/${speaker.toLowerCase()}`;
    },
    getText(speaker, filter) {
      let text = filter == 'plays' 
        ? `${speaker[filter].length} plays`
        : `${speaker[filter]} lines`;
      return text;
    }
  },
  computed: {
    filteredSpeakers: function() {
      let filtered = this.speakers.filter(speaker => {
        if (this.filterPlay === "any") return true;
        return speaker.play.contains(this.filteredPlay);
      });

      let sorted = filtered.sort((a, b) => {
        if (this.selectedSort == "plays") {
          return b[this.selectedSort].length - a[this.selectedSort].length;
        }
        return b[this.selectedSort] - a[this.selectedSort];
      });
      debugger;
      return sorted;
    }
  },
  created() {
    this.loading = true;
    axios
      .get("speakers")
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

<style scoped>
</style>