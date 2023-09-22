const mongoRepository = require('./mongo/mongo-repository');
const locolRepository = require('./json/strava-repository-adapter')

mongoRepository.connect()
.then(async () => await saveActivities())

async function saveActivities() {
  return locolRepository.getAllRecordedActivities()
  .then(athleteActivities => {
    let promises = athleteActivities.map(athleteActivity => {
      return locolRepository.getActivityById(athleteActivity.id)
      .then(act => mongoRepository.addNewActivity(act))
      .then(_ => mongoRepository.addNewAthleteActivities(athleteActivity))
      .catch(e => {
        console.error("Error !", e);
      })
    });
    return Promise.all(promises);
  })
  .then(_ => console.log("All activities saved !"))
}
