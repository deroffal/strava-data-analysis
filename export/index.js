const { createStravaApiClient } = require("./adapters/strava-api-adapter");
const repository = require("./adapters/strava-repository-adapter");

(async () => {
  console.info("start...")
  let stravaClient = await createStravaApiClient()

  await repository.getLastRecordedActivityDate()
  .then(startDate => stravaClient.getLoggedInAthleteActivities(startDate))
  .then(saveNewActivities)

  function saveNewActivities(newAthleteActivities) {
    if (newAthleteActivities.length > 0) {
      let promises = newAthleteActivities.map(fetchActivityData)
      return Promise.all(promises)
    } else {
      console.info("No new activity.")
      return Promise.resolve();
    }
  }

  function fetchActivityData(newAthleteActivity) {
    return repository.addNewAthleteActivities(newAthleteActivity)
    .then(athleteActivity => stravaClient.getActivityById(athleteActivity.id))
    .then(activity => repository.addNewActivity(activity))
    .catch(err => {
      console.error("Error : " + err)
      repository.removeAthleteActivities(newAthleteActivity.id)
    })

  }

  console.info("end...")
  process.exit(0)
})()

