const {readFile, writeFile} = require("./json-file-adapter");

async function getLastRecordedActivityDate() {
    let currentAthleteActivities = await readFile('athlete_activities.json')
    if (currentAthleteActivities.length !== 0) {
        return new Date(currentAthleteActivities[currentAthleteActivities.length - 1]['start_date'])
    } else {
        return new Date(2010, 0, 1)
    }

}

async function addNewAthleteActivities(newActivity) {
    let activities = await readFile('athlete_activities.json')
    activities.push(newActivity);
    await writeFile(activities, 'athlete_activities.json')
    return newActivity
}

async function removeAthleteActivities(id) {
    let activities = await readFile('athlete_activities.json')
    await writeFile(activities.filter(activity => activity.id !== id), 'athlete_activities.json')
}

async function addNewActivity(newActivity) {
    await writeFile(newActivity, `activities/${newActivity.id}.json`)
}

module.exports = {getLastRecordedActivityDate, addNewAthleteActivities, removeAthleteActivities, addNewActivity}
