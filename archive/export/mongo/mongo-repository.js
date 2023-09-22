const mongoose = require("mongoose");
const env = require("../env")
const { AthleteActivity, Activity } = require('./models');

async function connect() {
  let mongoProperties = env.mongo_properties;
  let url = `mongodb://${mongoProperties.login}:${mongoProperties.password}@${mongoProperties.cluster}/?retryWrites=true&w=majority`;
  return mongoose.connect(
    url,
    {
      useNewUrlParser: true,
      useUnifiedTopology: true
    })
  .then(_ => console.info('Database connected !'))
  .catch(e => {
    console.error('Cannot connect to database !', e);
    throw e;
  });
}

async function addNewAthleteActivities(newActivity) {
  return new AthleteActivity(newActivity).save()
}

async function addNewActivity(newActivity) {
  return new Activity(newActivity).save()
}

async function getLastRecordedActivityDate() {
  let athleteActivity = await AthleteActivity.findOne({}, 'start_date')
  .sort('-start_date');
  return athleteActivity.start_date;
}

async function removeAthleteActivities(id) {
  return AthleteActivity.deleteOne({ id: id })
}

module.exports = {
  connect,
  addNewAthleteActivities,
  addNewActivity,
  getLastRecordedActivityDate,
  removeAthleteActivities
}
