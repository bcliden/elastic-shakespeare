import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Plays from '../views/Plays.vue'
import Speakers from '../views/Speakers.vue'
import Speaker from '../views/Speaker.vue';
import Play from '../views/Play.vue';

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/plays/:name',
    name: 'plays (indiv)',
    component: Play
  },
  {
    path: '/plays',
    name: 'plays',
    component: Plays
  },
  {
    path: '/speakers/:name',
    name: 'speakers (indiv)',
    component: Speaker
  },
  {
    path: '/speakers',
    name: 'speakers',
    component: Speakers
  }
];

const router = new VueRouter({
  routes
})

export default router
