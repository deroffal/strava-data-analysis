const SwaggerClient = require('swagger-client');

class SwaggerApi {
    constructor(token) {
        this.token = token
    }

    async _doInitialize() {
        const requestInterceptor = (request) => {
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
    }

    //FIXME handle pagination
    async getLoggedInAthleteActivities(start_date) {
        let time = new Date(start_date).getTime() / 1_000;
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
}

async function createStravaApiClient(token) {
    let client = new SwaggerApi(token)
    await client._doInitialize()
    return client
}


module.exports = {createStravaApiClient}
