const {createStravaApiClient} = require("./adapters/strava-api-adapter");
const repository = require("./adapters/strava-repository-adapter");

(async () => {
    console.info("start...")
    let startDate = await repository.getLastRecordedActivityDate();

    let client = await getStravaApiClient();
    let newActivities = await client.getLoggedInAthleteActivities(startDate)
    if (newActivities.length > 0) {

        // FIXME synchronous to avoid "too many request" error to de-synchronize athlete_activities.json and activites/*
        for (const newActivity of newActivities) {
            await repository.addNewAthleteActivities(newActivity)
                .then(activity => client.getActivityById(activity.id), activity => {
                    repository.removeAthleteActivities(activity.id);
                })
                .then(repository.addNewActivity)
        }

    } else {
        console.info("No new activity.")
    }
    console.info("end...")
})()

async function getStravaApiClient() {
    let token = getToken();
    return createStravaApiClient(token);
}

function getToken() {
    let token = process.env['STRAVA_TOKEN'];
    if (!token) {
        throw new Error("Please provide a value for env STRAVA_TOKEN !")
    }
    return token;
}


