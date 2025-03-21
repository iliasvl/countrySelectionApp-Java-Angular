export const environment = {
    production: true,
    apiUrl: window.location.hostname === 'localhost' ? 'http://localhost:8080' : 'http://backend:8080', 
    enableAnalytics: true,
    featureFlag: false 
  };