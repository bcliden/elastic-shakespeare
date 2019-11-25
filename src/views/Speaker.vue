<template>
  <v-container>
    <v-row>
      <Title>{{ this.$route.params.name }}</Title>
    </v-row>
    <v-row>
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">Play</th>
              <th class="text-left">Line No.</th>
              <th class="text-left">Text</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in lines" :key="item.line_id">
              <td>{{ item.play_name }}</td>
              <td>{{ item.line_number }}</td>
              <td>{{ item.text_entry }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import Title from "../components/Title.vue";

export default {
  data: () => ({
    lines: null
  }),
  created() {
    axios
      .get(`http://localhost:8080/api/speakers/${this.$route.params.name}`)
      .then(({ data }) => {
        this.lines = data;
      })
      .catch(console.error);
  },
  components: {
    Title
  }
};
</script>

<style>
</style>