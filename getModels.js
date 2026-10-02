const https = require('https');
https.get('https://openrouter.ai/api/v1/models', (resp) => {
  let data = '';
  resp.on('data', (chunk) => { data += chunk; });
  resp.on('end', () => {
    try {
        const models = JSON.parse(data).data;
        const freeModels = models.filter(m => m.id.endsWith(':free')).map(m => m.id);
        console.log("FREE MODELS:", freeModels.join(', '));
    } catch(e) {
        console.log("Error:", e);
    }
  });
});
