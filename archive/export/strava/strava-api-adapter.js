const SwaggerClient = require('swagger-client');
const env = require('../env');

async function createStravaApiClient() {
  return getAccessToken()
  .then(token => new SwaggerApi(token))
  .then(client => client._doInitialize())
}

class SwaggerApi {
  constructor(token) {
    this.token = token
  }

  async _doInitialize() {
    const requestInterceptor = (request) => {
      let proxy = process.env['HTTP_PROXY'];
      if (proxy) {
        const HttpsProxyAgent = require('https-proxy-agent')
        request.agent = new HttpsProxyAgent(proxy)
      }
      request.headers["Authorization"] = `Bearer ${this.token}`
      return request;
    };

    let client = await new SwaggerClient({
      url: 'https://developers.strava.com/swagger/swagger.json',
      requestInterceptor
    });

    this.activitiesAPI = client.apis.Activities
    this.athletesAPI = client.apis.Athletes
    this.segmentsAPI = client.apis.Segments

    return this;
  }

  //FIXME handle pagination
  async getLoggedInAthleteActivities(start_date) {
    let time = start_date.getTime() / 1_000;
    let loggedInAthleteActivities = this.activitiesAPI.getLoggedInAthleteActivities({
      after: time,
      per_page: 100
    })
    return loggedInAthleteActivities
    .then(
      response => JSON.parse(response.data),
      console.error
    );
  }

  async getActivityById(id) {
    let activityById = this.activitiesAPI.getActivityById({ id: id });
    return activityById
    .then(
      response => JSON.parse(response.data),
      console.error
    );
  }

}

async function getAccessToken() {
  let accessToken = env.getAccessToken();
  if (accessToken) {
    return accessToken;
  }
  let properties = env.refresh_token_properties;

  return fetch(
    `https://www.strava.com/api/v3/oauth/token?client_id=${properties.clientId}&client_secret=${properties.clientSecret}&grant_type=refresh_token&refresh_token=${properties.refreshToken}`,
    { method: 'POST', redirect: 'follow' }
  )
  .then(response => response.json())
  .then(json => json.access_token)
  .catch(error => console.log('error', error));
}

module.exports = { createStravaApiClient }
