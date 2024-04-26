import axios from 'axios';  
  
const instance = axios.create();  
  
// 请求拦截器  
instance.interceptors.request.use(config => {  
    const token = localStorage.getItem('userToken'); // 从localStorage中获取Token  
    if (token) {  
        // 将Token添加到请求的头部中  
        // console.log('token:'+token)
        // config.headers['token'] = token
        config.headers.Authorization = `Bearer ${token}`;  
    }  
    return config;  
}, error => {  
    // 处理请求错误  
    return Promise.reject(error);  
});  
  
// 使用实例发送请求  
instance.get('/api/some-endpoint')  
    .then(response => {  
        // 处理响应数据  
    })  
    .catch(error => {  
        // 处理错误  
    });
//导出
export default instance;