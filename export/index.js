const {createStravaApiClient} = require("./adapters/strava-adapter");

let start_date = '2022-01-01';

(async () => {
    console.log("start...")
    let token = getToken();
    let client = await createStravaApiClient(token);
    let athleteActivities = await client.getLoggedInAthleteActivities(start_date)

    console.log(`athleteActivities : ${athleteActivities.length}`)

    console.log("end...")


})()

function getToken() {
    let token = process.env['STRAVA_TOKEN'];
    if (!token) {
        throw new Error("Please provide a value for env STRAVA_TOKEN !")
    }
    return token;
}


