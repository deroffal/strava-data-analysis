const {createStravaApiClient} = require("./adapters/strava-api-adapter");
const {
    getLastRecordedActivityDate,
    addNewActivitiesToAthleteActivities,
    addNewActivity
} = require("./adapters/strava-repository-adapter");

(async () => {
    console.info("start...")
    let startDate = await getLastRecordedActivityDate();

    let client = await getStravaApiClient();
    let newActivities = await client.getLoggedInAthleteActivities(startDate)
    if (newActivities.length > 0) {

        await addNewActivitiesToAthleteActivities(newActivities)

        let activityPromises = newActivities.map(activity => activity.id)
            .map(id =>
                client.getActivityById(id)
                    .then(activity => addNewActivity(activity))
            )

        await Promise.all(activityPromises)
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


