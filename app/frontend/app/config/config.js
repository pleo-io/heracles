const config = {
    app: {
      port: 3000
    },
    service: {
        url: process.env.BACKEND_SERVICE_URL ||  'http://localhost:8080/format'
    }
};
   
module.exports = config;