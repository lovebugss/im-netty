import {defineConfig} from 'umi';

export default defineConfig({
  nodeModulesTransform: {
    type: 'none',
  },
  routes: [
    {path: '/', component: '@/pages/index'},
    {
      path: '/chat',
      wrappers:[ "@/wrappers/auth"],
      component: '@/pages/chat/index'},
  ],
  fastRefresh: {},
  proxy: {
    '/api/': {
      'target': 'http://localhost:8580',
      'changeOrigin': true,
      'pathRewrite': { '^/api' : '' },
    },
  },
  antd: {},
});
