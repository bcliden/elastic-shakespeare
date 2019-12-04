<template>
  <v-container class="pa-0">
    <v-row>
      <Title>
        {{ formatted(this.$route.params.name) }}
        <template v-slot:subtitle>play</template>
      </Title>
    </v-row>
    <v-row>
      <v-card width="100%">
        <v-card-title>
          <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table :headers="headers" :items="lines" :search="search" :loading="loading">
          <template v-slot:item.speaker="{ item }">
            <router-link :to="getSpeakerURL(item.speaker)">{{ item.speaker }}</router-link>
          </template>
        </v-data-table>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import Title from "../components/Title.vue";

export default {
  data: () => ({
    lines: [],
    loading: true,
    search: "",
    headers: [
      { text: "line_number", value: "line_number", width: "10%" },
      { text: "speaker", value: "speaker", width: "15%" },
      { text: "text", value: "text_entry" }
    ]
  }),
  methods: {
    formatted: function(name) {
      let newName = name
        .toLowerCase()
        .split(" ")
        .map(word => {
          if (word == "iv" || word == "vi" || word == "viii" || word == "ii" || word == "iii") {
            return word.slice(0).toUpperCase();
          }
          return word;
        })
        .map(word => {
          let capWord = [...word];
          capWord[0] = capWord[0].toUpperCase();
          return capWord.join("");
        })
        .join(" ");
      return newName;
    },
    init(name) {
      this.loading = true;
      axios
        .get(`plays/${name}`)
        .then(({ data }) => {
          this.loading = false;
          this.lines = data;
        })
        .catch(err => {
          this.loading = false;
          console.error(err);
        });
    },
    getSpeakerURL(name) {
      return `/speakers/${name.toLowerCase()}`;
    }
  },
  created() {
    this.init(this.$route.params.name);
  },
  beforeRouteUpdate(to, from, next) {
    this.init(to.params.name);
    next();
  },
  components: {
    Title
  }
};
</script>

<style>
</style>