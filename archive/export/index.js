const { createStravaApiClient } = require("./strava/strava-api-adapter");
const mongoRepository = require('./mongo/mongo-repository');

(async () => {
  console.info("start...")
  let stravaClient = await createStravaApiClient()
  await mongoRepository.connect();

  await mongoRepository.getLastRecordedActivityDate()
  .then(startDate => stravaClient.getLoggedInAthleteActivities(startDate))
  .then(saveNewActivities)

  function saveNewActivities(newAthleteActivities) {
    if (newAthleteActivities.length > 0) {
      console.info(`Registering ${newAthleteActivities.length} activity(ies) ...`)
      let promises = newAthleteActivities.map(fetchActivityData)
      return Promise.all(promises)
    } else {
      console.info("No new activity.")
      return Promise.resolve();
    }
  }

  function fetchActivityData(newAthleteActivity) {
    console.debug(`Fetching activity ${newAthleteActivity.id}`)
    return mongoRepository.addNewAthleteActivities(newAthleteActivity)
    .then(athleteActivity => stravaClient.getActivityById(athleteActivity.id))
    .then(activity => mongoRepository.addNewActivity(activity))
    .catch(err => {
      console.error("Error : " + err)
      mongoRepository.removeAthleteActivities(newAthleteActivity.id)
    })

  }

  console.info("end...")
  process.exit(0)
})()
