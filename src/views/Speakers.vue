<template>
  <v-container>
      <Title>
          Speakers
          <template v-slot:subtitle>top 100 by lines spoken</template>
      </Title>
    <v-row>
        <v-card v-for="(speaker, i) in speakers" :key="i" class="ma-2" link :to="getUrl(speaker.speaker)">
            <v-card-title>
                {{ speaker.speaker }}
            </v-card-title>
            <v-card-subtitle>
                {{ formatted(speaker.lines) }} lines
            </v-card-subtitle>
        </v-card>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import Title from '../components/Title.vue';

export default {
    data: () => ({
        speakers: [
            {"speaker": "FALSTAFF", "lines": 1000000}
        ]
    }),
    methods: {
        formatted(number){
            return new Number(number).toLocaleString();
        },
        getUrl(speaker){
            return `speakers/${speaker.toLowerCase()}`;
        }
    },
    created() {
        axios.get('http://localhost:8080/api/speakers')
            .then(({ data }) => {
                this.speakers = data;
            })
            .catch(console.error)
    },
    components: {
        Title
    }
};
</script>

<style>
</style>