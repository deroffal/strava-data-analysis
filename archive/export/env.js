function getAccessToken() {
  return process.env['STRAVA_ACCESS_TOKEN'];
}

const refresh_token_properties = {
  refreshToken: process.env['STRAVA_REFRESH_TOKEN'],
  clientId: process.env['STRAVA_CLIENT_ID'],
  clientSecret: process.env['STRAVA_CLIENT_SECRET'],
}

const mongo_properties = {
  login: process.env['MONGO_LOGIN'],
  password: process.env['MONGO_PASSWORD'],
  cluster: process.env['MONGO_CLUSTER'],
  // schema: process.env['MONGO_SCHEMA'],
}

module.exports = { getAccessToken, refresh_token_properties, mongo_properties }
